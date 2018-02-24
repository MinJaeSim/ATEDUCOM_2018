package ac.ajou.simminje.ateducom.trigger;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ac.ajou.simminje.ateducom.R;

public class TriggerFragment extends Fragment implements TriggerContract.View, TriggerDialogFragment.OnTriggerEventListener {

    private TriggerContract.Presenter triggerPresenter;

    private TriggerAdapter likeAdapter;
    private TriggerAdapter dislikeAdapter;

    private FloatingActionButton addButton;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trigger, container, false);

        triggerPresenter = new TriggerPresenter();
        triggerPresenter.setView(this);

        addButton = view.findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            showEditDietDialog(null, "");
        });

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        RecyclerView likeRecyclerView = view.findViewById(R.id.recycler_view_like);
        LinearLayoutManager likeLayoutManager = new LinearLayoutManager(getContext());
        likeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        likeAdapter = new TriggerAdapter(FirebaseFirestore.getInstance().collection("Trigger").whereEqualTo("uid", uid).whereEqualTo("type", 0));
        likeRecyclerView.setLayoutManager(likeLayoutManager);
        likeRecyclerView.setAdapter(likeAdapter);

        RecyclerView dislikeRecyclerView = view.findViewById(R.id.recycler_view_dislike);
        LinearLayoutManager dislikeLayoutManager = new LinearLayoutManager(getContext());
        dislikeLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dislikeAdapter = new TriggerAdapter(FirebaseFirestore.getInstance().collection("Trigger").whereEqualTo("uid", uid).whereEqualTo("type", 1));
        dislikeRecyclerView.setLayoutManager(dislikeLayoutManager);
        dislikeRecyclerView.setAdapter(dislikeAdapter);

        List<TriggerAdapter> adapters = new ArrayList<>();
        adapters.add(likeAdapter);
        adapters.add(dislikeAdapter);

        triggerPresenter.setAdapter(adapters);

        return view;
    }

    @Override
    public void showEditDietDialog(Trigger trigger, String key) {
        TriggerDialogFragment dialogFragment = new TriggerDialogFragment();
        if (trigger != null)
            dialogFragment.setTrigger(trigger);
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setOnDietEventListener(this);
        dialogFragment.show(getFragmentManager(), "test");
    }

    @Override
    public void showRemoveDietDialog(Trigger trigger, String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to remove trigger?");
        builder.setPositiveButton("Yes",
                (dialog, which) -> {
                    triggerPresenter.removeDiet(trigger, key);
                    likeAdapter.notifyDataSetChanged();
                    dislikeAdapter.notifyDataSetChanged();
                });
        builder.setNegativeButton("No",
                (dialog, which) -> {

                });
        builder.show();
    }

    @Override
    public void onConfirm(Trigger trigger) {
        triggerPresenter.addTrigger(trigger);
        likeAdapter.notifyDataSetChanged();
        dislikeAdapter.notifyDataSetChanged();
    }
}
