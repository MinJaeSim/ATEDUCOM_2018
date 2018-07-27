package ac.ajou.simminje.ateducom.trigger;


import android.support.annotation.NonNull;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.List;

public class TriggerPresenter implements TriggerContract.Presenter, OnTriggerClickListener {

    private FirebaseFirestore firebaseFirestore;
    private TriggerContract.View view;
    private List<TriggerAdapter> triggerAdapters;

    public TriggerPresenter() {
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(@NonNull Trigger trigger, String documentKey) {
        view.showEditDietDialog(trigger, documentKey);
    }

    @Override
    public void onDeleteClick(@NonNull Trigger trigger, @NonNull String documentKey) {
        view.showRemoveDietDialog(trigger, documentKey);
    }

    @Override
    public void setView(TriggerContract.View view) {
        this.view = view;
    }

    @Override
    public void setAdapter(List<TriggerAdapter> adapters) {
        this.triggerAdapters = adapters;

        for (TriggerAdapter adapter : triggerAdapters) {
            adapter.setOnTriggerClickListener(this);
        }
    }

    @Override
    public void addTrigger(Trigger trigger) {
        Query query = firebaseFirestore.collection("Trigger").whereEqualTo("id", trigger.getId());
        query.get().addOnSuccessListener(documentSnapshots -> {
            if (documentSnapshots.isEmpty()) {
                firebaseFirestore.collection("Trigger").add(trigger);
            } else {
                String documentKey = documentSnapshots.getDocuments().get(0).getId();
                updateDiet(trigger, documentKey);
            }
        });
    }

    @Override
    public void updateDiet(Trigger trigger, String key) {
        firebaseFirestore.collection("Trigger").document(key).set(trigger);
    }

    @Override
    public void removeDiet(Trigger trigger, String key) {
        firebaseFirestore.collection("Trigger").document(key).delete();
    }
}
