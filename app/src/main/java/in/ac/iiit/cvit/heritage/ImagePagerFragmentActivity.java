package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import java.util.ArrayList;

public class ImagePagerFragmentActivity extends FragmentActivity {

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

        sessionManager = new SessionManager();
        final String packageName = sessionManager.getStringSessionPreferences(ImagePagerFragmentActivity.this, "package_name", "");

        Intent intent = getIntent();
        final String text_interest_point = intent.getStringExtra("interest_point");
        interestPoint = LoadInterestPoint(packageName, text_interest_point);
        _images = interestPoint.getImages();

        viewPager = (ViewPager) findViewById(R.id.viewpager_images);
        imagePagerAdapter = new ImagePagerAdapter(getSupportFragmentManager(), _images.size());
        viewPager.setAdapter(imagePagerAdapter);
        //viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public InterestPoint LoadInterestPoint(String packageName, String interestPointName) {
        PackageReader reader;
        packageName = packageName.toLowerCase();
        reader = new PackageReader(packageName);
        ArrayList<InterestPoint> interestPoints = reader.getContents();

        InterestPoint interestPoint;
        for (int i=0; i<interestPoints.size(); i++) {
            interestPoint = interestPoints.get(i);
            if (interestPoint.get("title").equals(interestPointName)) {
                return interestPoint;
            }
        }
        return null;
    }
}
