package ac.ajou.simminje.ateducom.routine;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;

public class RoutinePresenter implements RoutineContract.Presenter, OnRoutineClickListener {

    private Context context;
    private FirebaseFirestore firebaseFirestore;
    private RoutineContract.View view;
    private RoutineAdapter adapter;

    private static AlarmManager alarmManager;
    private static Calendar calendar;

    public RoutinePresenter(Context context) {
        this.context = context;
        firebaseFirestore = FirebaseFirestore.getInstance();
        calendar = Calendar.getInstance();
        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void setView(RoutineContract.View view) {
        this.view = view;
    }

    @Override
    public void setAdapter(RoutineAdapter adapter) {
        this.adapter = adapter;
        this.adapter.setOnRoutineClickListener(this);
    }

    @Override
    public void addRoutine(Routine routine) {
        Query query = firebaseFirestore.collection("Routine").whereEqualTo("routineId", routine.getRoutineId());
        query.get().addOnSuccessListener(documentSnapshots -> {
            if (documentSnapshots.isEmpty()) {
                firebaseFirestore.collection("Routine").add(routine);
                routineOnOff(routine);
            } else {
                String documentKey = documentSnapshots.getDocuments().get(0).getId();
                updateRoutine(routine, documentKey);
            }
        });
    }

    @Override
    public void updateRoutine(Routine routine, String key) {
        routine.setOn(routine.isOn());
        routine.setTime(routine.getTime());
        routine.setUserName(routine.getUserName());

        firebaseFirestore.collection("Routine").document(key).set(routine);
        routineOnOff(routine);
    }

    @Override
    public void routineOnOff(Routine routine) {
        Intent alarmIntent = new Intent("android.intent.action.ALARM_START");
        PendingIntent pendingIntent;

        if (!routine.isOn()) {
            pendingIntent = PendingIntent.getBroadcast(context, routine.getRoutineId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
        } else {

            boolean isRepeat = false;

            String[] time = routine.getTime().split(":");
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            calendar.set(Calendar.SECOND, 0);

            int diff = (int) (Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis());

            if (isRepeat) {
                alarmIntent.putExtra("repeat", true);
                pendingIntent = PendingIntent.getBroadcast(context, routine.getRoutineId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                if (diff >= 0)
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (24 * 60 * 60 * 1000), AlarmManager.INTERVAL_DAY, pendingIntent);
                else
                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            } else {
                alarmIntent.putExtra("repeat", false);
                pendingIntent = PendingIntent.getBroadcast(context, routine.getRoutineId(), alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                if (diff >= 0)
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() + (24 * 60 * 60 * 1000), pendingIntent);
                else
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }

    @Override
    public void removeAlarm(String key) {
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Routine").document(key).delete();
    }

    @Override
    public void onClick(@NonNull Routine routine, String documentKey) {
        view.showEditRoutineDialog(routine, documentKey);
    }

    @Override
    public void onDeleteClick(@NonNull String documentKey) {
        view.showRemoveRoutineDialog(documentKey);
    }
}
