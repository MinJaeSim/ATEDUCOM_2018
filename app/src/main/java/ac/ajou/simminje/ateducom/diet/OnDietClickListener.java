package ac.ajou.simminje.ateducom.diet;


import android.support.annotation.NonNull;

import ac.ajou.simminje.ateducom.routine.Routine;

public interface OnDietClickListener {
    void onClick(@NonNull Diet diet, String documentKey);

    void onDeleteClick(@NonNull Diet diet, @NonNull String documentKey);
}
