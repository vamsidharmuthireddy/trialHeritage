package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestPointActivity extends AppCompatActivity {
    /**
     * When an interest point is clicked, this class is called.
     * It sets the data on the interest point activity
     */
    private Toolbar toolbar;
    private ImageView imageView;
    private TextView textview_info;
    private CardView cardview_images;
    private CardView cardview_videos;
    private CardView cardview_audio;
    private TextView textview_images;
    private TextView textview_videos;
    private TextView textview_audio;
    private ImageView imageview_images;
    private InterestPoint interestPoint;
    private SessionManager sessionManager;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private static final String LOGTAG = "Heritage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interest_point);

        //we are getting the name of the session(Heritage site that user initially clicked to see)
        sessionManager = new SessionManager();
        final String packageName = sessionManager
                .getStringSessionPreferences(
                        InterestPointActivity.this, getString(R.string.package_name), getString(R.string.default_package_value));

        //we are getting tha name of the interest point that was clicked
        Intent intent = getIntent();
        final String text_interest_point = intent.getStringExtra(getString(R.string.clicked_interest_point));
        interestPoint = LoadInterestPoint(packageName, text_interest_point);

        toolbar = (Toolbar) findViewById(R.id.coordinatorlayout_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(text_interest_point);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.coordinatorlayout_colltoolbar);
        collapsingToolbarLayout.setTitle(text_interest_point);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbarLayout.setStatusBarScrimColor(getResources().getColor(R.color.colorPrimaryDark));
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.ToolbarStyle);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ToolbarStyle);

        imageView = (ImageView) findViewById(R.id.coordinatorlayout_imageview);
        imageView.setImageBitmap(interestPoint.getImage());


        textview_info = (TextView) findViewById(R.id.cardview_text);
        textview_info.setText(interestPoint.get(getString(R.string.interest_point_info)));

        cardview_images = (CardView) findViewById(R.id.card_images);
        textview_images = (TextView) cardview_images.findViewById(R.id.cardview_text);
        cardview_videos = (CardView) findViewById(R.id.card_videos);
        textview_videos = (TextView) cardview_videos.findViewById(R.id.cardview_text);
        cardview_audio = (CardView) findViewById(R.id.card_audio);
        textview_audio = (TextView) cardview_audio.findViewById(R.id.cardview_text);

        textview_images.setText(R.string.images);
        textview_videos.setText(R.string.videos);
        textview_audio.setText(R.string.audio);

        //setting onClickListener for images button of the interest point
        cardview_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the ImagePagerFragmentActivity to display the relevant images of the selected interest point
                Intent intent_image_slider = new Intent(InterestPointActivity.this, ImagePagerFragmentActivity.class);
                intent_image_slider.putExtra(getString(R.string.clicked_interest_point), text_interest_point);
                startActivity(intent_image_slider);
            }
        });

        //setting onClickListener for videos button of the interest point
        cardview_videos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //setting onClickListener for audios button of the interest point
        cardview_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    /**
     * This method checks for the clicked interest point by it's name in the database
     * @param packageName Name of the Heritage site that usr chooses initially
     * @param interestPointName Clicked interest point name
     * @return  clicked InterestPoint object
     */
    public InterestPoint LoadInterestPoint(String packageName, String interestPointName) {
        PackageReader reader;
        packageName = packageName.toLowerCase();
        reader = new PackageReader(packageName);
        ArrayList<InterestPoint> interestPoints = reader.getContents();

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
