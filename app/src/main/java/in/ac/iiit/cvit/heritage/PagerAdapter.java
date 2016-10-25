package in.ac.iiit.cvit.heritage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int _tabCount;

    public PagerAdapter(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        _tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                NearbyPointsFragment nearbyPointsFragment = new NearbyPointsFragment();
                return nearbyPointsFragment;
            case 2:
                InterestPointsFragment interestPointsFragment = new InterestPointsFragment();
                return interestPointsFragment;
            default:
                return null;
        }
    }


    @Override
    public int getCount() {
        return _tabCount;
    }
}
