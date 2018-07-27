package ac.ajou.simminje.ateducom;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPageAdapter extends FragmentPagerAdapter {
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new MainFragment();
        if (position < 0)
            return null;

        switch (position) {
            case 0:
                fragment = new MainFragment();
                break;
            case 1:
                fragment = new CameraFragment();
                break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
