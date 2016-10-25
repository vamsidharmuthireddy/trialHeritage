package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashIntroActivity extends AppCompatActivity {

    private Button button_next;
    private Button button_skip;
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    private TextView[] navigation_indicators;
    private SplashIntroAdapter splashIntroAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash_slider);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        viewPager = (ViewPager) findViewById(R.id.viewpager_splash);
        splashIntroAdapter = new SplashIntroAdapter(getSupportFragmentManager());
        viewPager.setAdapter(splashIntroAdapter);

        button_next =(Button) findViewById (R.id.button_next);
        button_skip =(Button) findViewById (R.id.button_skip);
        linearLayout = (LinearLayout) findViewById(R.id.layout_dots);

        addNavigationIndicators(0);

        button_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPackagesListScreen();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int current_item = viewPager.getCurrentItem() + 1;
                if (current_item < 3) {
                    viewPager.setCurrentItem(current_item);
                } else {
                    launchPackagesListScreen();
                }
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addNavigationIndicators(position);
                if (position == 2) {
                    button_next.setText(R.string.got_it);
                    button_skip.setVisibility(View.GONE);
                } else {
                    button_next.setText(R.string.next);
                    button_skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void launchPackagesListScreen() {
        Intent intent_packages_list = new Intent(SplashIntroActivity.this, PackagesListActivity.class);
        intent_packages_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_packages_list);
        finish();
    }

    private void addNavigationIndicators(int current_page) {
        navigation_indicators = new TextView[3];
        linearLayout.removeAllViews();

        for (int i=0; i<3; i++) {
            navigation_indicators[i] = new TextView(SplashIntroActivity.this);
            navigation_indicators[i].setText(Html.fromHtml("&#8226;"));
            navigation_indicators[i].setTextSize(36);
            navigation_indicators[i].setTextColor(getResources().getColor(R.color.colorGray));
            linearLayout.addView(navigation_indicators[i]);
        }

        if (navigation_indicators.length > 0) {
            navigation_indicators[current_page].setTextColor(getResources().getColor(R.color.colorWhite));
        }
    }
}
