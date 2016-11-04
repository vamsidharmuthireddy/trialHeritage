package in.ac.iiit.cvit.heritage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * This class is used as adapter to view the images when Image button is clicked in InterestPointActivity
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private int _tabCount;

    private static final String LOGTAG = "Heritage";

    public ImagePagerAdapter(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        _tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        ImageViewFragment imageViewFragment = new ImageViewFragment();

        Bundle args = new Bundle();
        args.putInt("image_number", position);
        imageViewFragment.setArguments(args);

        return imageViewFragment;
    }

    @Override
    public int getCount() {
        return _tabCount;
    }

}
