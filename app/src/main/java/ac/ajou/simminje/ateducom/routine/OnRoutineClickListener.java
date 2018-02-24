package ac.ajou.simminje.ateducom.routine;


import android.support.annotation.NonNull;

public interface OnRoutineClickListener {
    void onClick(@NonNull Routine routine, String documentKey);

    void onDeleteClick(@NonNull String documentKey);
}
