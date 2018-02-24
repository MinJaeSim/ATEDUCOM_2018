package ac.ajou.simminje.ateducom.trigger;


import java.util.List;

public interface TriggerContract {
    interface View {
        void showEditDietDialog(Trigger trigger, String key);

        void showRemoveDietDialog(Trigger trigger, String key);
    }

    interface Presenter {
        void setView(TriggerContract.View view);

        void setAdapter(List<TriggerAdapter> adapters);

        void addTrigger(Trigger trigger);

        void updateDiet(Trigger trigger, String key);

        void removeDiet(Trigger trigger, String key);
    }
}
