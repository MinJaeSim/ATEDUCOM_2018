package ac.ajou.simminje.ateducom.routine;


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

import ac.ajou.simminje.ateducom.R;

public class RoutineFragment extends Fragment implements RoutineContract.View, RoutineDialogFragment.OnRoutineDialogEventListener {

    private RoutineContract.Presenter routinePresenter;
    private RoutineAdapter adapter;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_routine, container, false);


        routinePresenter = new RoutinePresenter(getContext());
        routinePresenter.setView(this);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_like);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        String uid = FirebaseAuth.getInstance().getUid();
        adapter = new RoutineAdapter(FirebaseFirestore.getInstance().collection("Routine").whereEqualTo("uid", uid));

        recyclerView.setAdapter(adapter);

        routinePresenter.setAdapter(adapter);

        FloatingActionButton floatingActionButton = view.findViewById(R.id.add_button);
        floatingActionButton.setOnClickListener(v -> showEditRoutineDialog(null, ""));
        return view;
    }

    @Override
    public void showEditRoutineDialog(Routine routine, String key) {
        RoutineDialogFragment dialogFragment = new RoutineDialogFragment();
        if (routine != null)
            dialogFragment.setRoutine(routine);
        dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        dialogFragment.setOnRoutineDialogEventListener(this);
        dialogFragment.show(getFragmentManager(), "test");
    }

    @Override
    public void showRemoveRoutineDialog(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Do you want to remove routine?");
        builder.setPositiveButton("Yes",
                (dialog, which) -> {
                    routinePresenter.removeAlarm(key);
                    adapter.notifyDataSetChanged();
                });
        builder.setNegativeButton("No",
                (dialog, which) -> {

                });
        builder.show();
    }

    @Override
    public void onConfirm(Routine routine) {
        routinePresenter.addRoutine(routine);
        adapter.notifyDataSetChanged();
    }
}
