package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    /**
     *View Pager, Tab layout, number of Tabs are  setup here
     * All the interest points and their relevant content are loaded into an InterestPoint array from PackageReader
     *
     */
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SessionManager sessionManager;

    public static ArrayList<InterestPoint> interestPoints;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 2;

    private static final String LOGTAG = "Heritage";

    /**
     * This method calls PagerAdapter object
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting the clicked heritage site as a new session
        sessionManager = new SessionManager();
        String packageName = sessionManager
                .getStringSessionPreferences(
                        MainActivity.this, getString(R.string.package_name), getString(R.string.default_package_value));

        //getting data related to all the interest points available
        //The above interestPoints has the data on all available interest points
        interestPoints = LoadPackage(packageName);

        //Setting the toolbar to display "Heritage"
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        //Setting up three tabs "HOME", "NEARBY", "PLACES"
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(R.string.home));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.nearby));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.places));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //linking viewpager with the tab layout
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

           }
        });

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(MainActivity.this,PackagesListActivity.class);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent_settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent_settings);
                break;
            case R.id.action_about_us:
                Intent intent_about_us = new Intent(MainActivity.this, AboutUsActivity.class);
                startActivity(intent_about_us);
                break;
            case R.id.action_instructions:
                Intent intent_instructions = new Intent(MainActivity.this, InstructionsActivity.class);
                intent_instructions.putExtra(getString(R.string.first_time_instructions),false);
                startActivity(intent_instructions);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * This method returns interest points of the chosen Heritage site by calling PackageReader class
     * @param packageName It is the name of the site that user wants to see
     * @return List of all the interest points along with their contents in an InterestPoint array
     */
    public ArrayList<InterestPoint> LoadPackage(String packageName){
        PackageReader reader;
        packageName = packageName.toLowerCase();
        reader = new PackageReader(packageName, MainActivity.this);
        //This reader has all the information about all the interest points
        //We are getting an array of InterestPoint objects
        ArrayList<InterestPoint> interestPoints = reader.getContents();
        //The above interestPoints has the data on all available interest points
        return interestPoints;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
            case PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                        openApplicationPermissions();
                    } else {
                        openApplicationPermissions();
                    }
                }
            }
        }
    }

    private void openApplicationPermissions() {
        final Intent intent_permissions = new Intent();
        intent_permissions.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent_permissions.addCategory(Intent.CATEGORY_DEFAULT);
        intent_permissions.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));

        intent_permissions.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent_permissions.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent_permissions.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        MainActivity.this.startActivity(intent_permissions);
    }
}
