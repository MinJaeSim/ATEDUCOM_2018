package ac.ajou.simminje.ateducom.routine;


public interface RoutineContract {
    interface View {
        void showEditRoutineDialog(Routine routine, String key);

        void showRemoveRoutineDialog(String key);
    }

    interface Presenter {
        void setView(RoutineContract.View view);

        void setAdapter(RoutineAdapter adapter);

        void addRoutine(Routine routine);

        void updateRoutine(Routine routine, String key);

        void routineOnOff(Routine routine);

        void removeAlarm(String key);
    }
}
