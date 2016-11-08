package in.ac.iiit.cvit.heritage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SplashIntroAdapter extends FragmentPagerAdapter {
    /**
     * This class gives back the page adapter to viewPager
     * @param fragmentManager It receives SplashIntroFragment
     */

    private Context context;

    public SplashIntroAdapter(FragmentManager fragmentManager, Context _context) {
        super(fragmentManager);
        context = _context;
    }

    @Override
    public Fragment getItem(int position) {
        SplashIntroFragment splashIntroFragment = new SplashIntroFragment();

        Bundle args = new Bundle();
        args.putInt(context.getString(R.string.page_number), position);
        splashIntroFragment.setArguments(args);

        return splashIntroFragment;
    }

    /**
     *
     * @return number of screens to be displayed when fresh installation. Default = 3
     */
    @Override
    public int getCount() {
        return Integer.parseInt(context.getString(R.string.splash_intro_displays));
    }
}
