package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;

public class ImagePagerFragmentActivity extends FragmentActivity {
    /**
     * Called from InterestPointActivity when Images button is clicked
     * This class shows the relevant images for the clicked interest point
     */

    private ViewPager viewPager;
    private InterestPoint interestPoint;
    private SessionManager sessionManager;
    private ImagePagerAdapter imagePagerAdapter;

    public ArrayList<Bitmap> _images;

    private static final String LOGTAG = "Heritage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_slider);

        //getting the name of the package selected by the user(For e.g. Golkonda)
        sessionManager = new SessionManager();
        final String packageName = sessionManager
                .getStringSessionPreferences(
                        ImagePagerFragmentActivity.this, getString(R.string.package_name), getString(R.string.default_package_value));

        //getting the name of the interest point selected by the user
        Intent intent = getIntent();
        final String clicked_interest_point = intent.getStringExtra(getString(R.string.clicked_interest_point));
        //Loading the relevant interest point
        interestPoint = LoadInterestPoint(packageName, clicked_interest_point);
        //Getting the relevant images for the selected interest point
        _images = interestPoint.getImages(packageName,clicked_interest_point);

        viewPager = (ViewPager) findViewById(R.id.viewpager_images);
        imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), _images.size(),this);
        viewPager.setAdapter(imagePagerAdapter);
        //viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * This method uses PackageReader class to get the InterestPoint array and gives back the relevant InterestPoint object
     * @param packageName Name of the selected package
     * @param interestPointName name of the selected interest point
     * @return InterestPoint object of the selected interest point
     */
    public InterestPoint LoadInterestPoint(String packageName, String interestPointName) {

        //Initializing the reader and getting the list of all the intterst points
        PackageReader reader;
        packageName = packageName.toLowerCase();
        reader = new PackageReader(packageName);
        ArrayList<InterestPoint> interestPoints = reader.getContents();

        //Searching for the relevant interest point and returning it
        InterestPoint interestPoint;
        for (int i=0; i<interestPoints.size(); i++) {
            interestPoint = interestPoints.get(i);
            if (interestPoint.get(getString(R.string.interest_point_title)).equals(interestPointName)) {
                return interestPoint;
            }
        }
        return null;
    }
}
