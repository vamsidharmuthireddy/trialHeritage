package in.ac.iiit.cvit.heritage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Loading the language preference
        LocaleManager localeManager = new LocaleManager(AboutUsActivity.this);
        localeManager.loadLocale();
        setContentView(R.layout.activity_about_us);
    }
}
