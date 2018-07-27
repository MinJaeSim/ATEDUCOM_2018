package ac.ajou.simminje.ateducom.trigger;


import android.support.annotation.NonNull;

public interface OnTriggerClickListener {
    void onClick(@NonNull Trigger trigger, String documentKey);

    void onDeleteClick(@NonNull Trigger trigger, @NonNull String documentKey);
}
