package in.ac.iiit.cvit.heritage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SplashIntroAdapter extends FragmentPagerAdapter {

    public SplashIntroAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        SplashIntroFragment splashIntroFragment = new SplashIntroFragment();

        Bundle args = new Bundle();
        args.putInt("page_number", position);
        splashIntroFragment.setArguments(args);

        return splashIntroFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
