package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class SplashActivity extends AppCompatActivity {

    private LaunchPreferenceManager launchPreferenceManager;

    private static final int SLEEP = 5;
    private static final String LOGTAG = "Heritage";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash);

        //Loading the language preference
        LocaleManager localeManager = new LocaleManager(SplashActivity.this);
        localeManager.loadLocale();

        //This is the activity that is seen when app is initialised

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);

        launchPreferenceManager = new LaunchPreferenceManager(SplashActivity.this);

        //creating a thread
        Thread background = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(SLEEP * 1000);    //This is supposed to display start screen for 5 seconds. Look at Handler's postDelayed


                    if (!launchPreferenceManager.isFirstTimeLaunch()) {
                        //From second time whenever this app is opened, this activity is shown
                        //Intent intent_packages_list = new Intent(SplashActivity.this, PackagesListActivity.class);
                        Intent intent_packages_list = new Intent(SplashActivity.this, MenuActivity.class);
                        intent_packages_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_packages_list);
                        finish();
                    } else {
                        //This activity is only shown when opened for the first time after installing
                        launchPreferenceManager.setFirstTimeLaunch(false);
                        Intent intent_splash_intro = new Intent(SplashActivity.this, SplashIntroActivity.class);
                        intent_splash_intro.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_splash_intro);
                        finish();
                    }
                } catch (Exception e) {
                    Log.e(LOGTAG, e.toString());
                }
            }
        };

        //starting the created thread
        background.start();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent_splash = new Intent(Intent.ACTION_MAIN);
        intent_splash.addCategory(Intent.CATEGORY_HOME);
        intent_splash.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent_splash);

        finish();
        System.exit(0);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Splash Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
