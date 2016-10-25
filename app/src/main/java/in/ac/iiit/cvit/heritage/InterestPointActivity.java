package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class InterestPointActivity extends AppCompatActivity {

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

        sessionManager = new SessionManager();
        final String packageName = sessionManager.getStringSessionPreferences(InterestPointActivity.this, "package_name", "");

        Intent intent = getIntent();
        final String text_interest_point = intent.getStringExtra("interest_point");
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
        textview_info.setText(interestPoint.get("info"));

        cardview_images = (CardView) findViewById(R.id.card_images);
        textview_images = (TextView) cardview_images.findViewById(R.id.cardview_text);
        cardview_videos = (CardView) findViewById(R.id.card_videos);
        textview_videos = (TextView) cardview_videos.findViewById(R.id.cardview_text);
        cardview_audio = (CardView) findViewById(R.id.card_audio);
        textview_audio = (TextView) cardview_audio.findViewById(R.id.cardview_text);

        textview_images.setText(R.string.images);
        textview_videos.setText(R.string.videos);
        textview_audio.setText(R.string.audio);

        cardview_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_image_slider = new Intent(InterestPointActivity.this, ImagePagerFragmentActivity.class);
                intent_image_slider.putExtra("interest_point", text_interest_point);
                startActivity(intent_image_slider);
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
