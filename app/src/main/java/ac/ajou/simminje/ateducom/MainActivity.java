package ac.ajou.simminje.ateducom;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import ac.ajou.simminje.ateducom.routine.RoutineFragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null)
            fm.beginTransaction().add(R.id.fragment_container, new RoutineFragment()).commit();

        bottomNavigation = findViewById(R.id.bottomNavigation);
        setMenuItemFontType();

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_routine:
                        Fragment routineFragment = new RoutineFragment();
                        fm.beginTransaction().replace(R.id.fragment_container, routineFragment).commit();
                        return true;
                    case R.id.action_situation:
                        Fragment situationFragment = new SituationFragment();
                        fm.beginTransaction().replace(R.id.fragment_container, situationFragment).commit();
                        return true;
                    case R.id.action_social:
                        Fragment socialFragment = new SocialFragment();
                        fm.beginTransaction().replace(R.id.fragment_container, socialFragment).commit();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                .setMessage("앱을 종료하시겠습니까?")
                .setPositiveButton("OK", (d, which) -> finish())
                .setNegativeButton("Cancel", (d, which) -> {
                }).create();
        dialog.show();
    }

    private void setMenuItemFontType() {
//        Menu menu = bottomNavigation.getMenu();
//        String[] menuTitle = getResources().getStringArray(R.array.bottom_nav_item_title);
//        Typeface type = Typeface.createFromAsset(getAssets(), "TitleBold.otf");
//
//        for (int i = 0; i < menu.size(); i++) {
//            MenuItem item = menu.getItem(i);
//            SpannableString spannableString = new SpannableString(menuTitle[i]);
//            spannableString.setSpan(new CustomTypeFace("", type), 0, spannableString.length(), SpannableString.SPAN_INCLUSIVE_INCLUSIVE);
//            item.setTitle(spannableString);
//        }
    }
}


