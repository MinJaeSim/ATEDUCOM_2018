package ac.ajou.simminje.ateducom;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ac.ajou.simminje.ateducom.diet.DietFragment;
import ac.ajou.simminje.ateducom.routine.RoutineFragment;
import ac.ajou.simminje.ateducom.trigger.TriggerFragment;

public class MainFragment extends Fragment {

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button routineButton = view.findViewById(R.id.button_routine);
        routineButton.setOnClickListener(v -> changeFragment(new RoutineFragment()));

        Button dietButton = view.findViewById(R.id.button_diet);
        dietButton.setOnClickListener(v -> changeFragment(new DietFragment()));

        Button basicButton = view.findViewById(R.id.button_trigger);
        basicButton.setOnClickListener(v -> changeFragment(new TriggerFragment()));

        Button situationButton = view.findViewById(R.id.button_situation);
        situationButton.setOnClickListener(v -> changeFragment(new SituationFragment()));

        Button entertainmentButton = view.findViewById(R.id.button_entertainment);
        entertainmentButton.setOnClickListener(v -> changeFragment(new EntertainmentFragment()));


//        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 10; i++)
//            list.add(i);
//        recyclerView.setAdapter(new Adapter(list));
//
        return view;
    }

    private void changeFragment(Fragment f) {
//        FragmentManager fm = getFragmentManager();
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, f).addToBackStack(null).commit();
    }
}
