package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Locale;

public class PackagesListActivity extends AppCompatActivity {
    private final String LOGTAG = "PackageListActivity";
    /**
     * This class shows the list of all the heritage site packages.
     * This class starts SessionManager class to set the session and opens MainActivity related to that session
     */
    private Toolbar toolbar;
    private Button button_download_packages;
    private ListView listview_package_list;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Loading the language preference
        final LocaleManager localeManager = new LocaleManager(PackagesListActivity.this);
        localeManager.loadLocale();
        setContentView(R.layout.activity_package_lists);

        sessionManager = new SessionManager();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.my_heritage_sites);
        setSupportActionBar(toolbar);


        //Setting the packages to be displayed to the user
        //String[] packages = {"Golconda", "Hampi"};
        String[] packages = getResources().getStringArray(R.array.my_packages);
        listview_package_list = (ListView) findViewById(R.id.listview_package_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PackagesListActivity.this, android.R.layout.simple_list_item_1, packages);
        listview_package_list.setAdapter(adapter);
        listview_package_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String currentLanguage = Locale.getDefault().getLanguage();

                TextView textView = (TextView) view;
                //getting the name of the package which was clicked and setting the session
                localeManager.changeLang(getString(R.string.english));
                String[] packages = getResources().getStringArray(R.array.my_packages);
                String packageName = packages[position].toLowerCase();
                sessionManager.setSessionPreferences(PackagesListActivity.this, getString(R.string.package_name), packageName);

                Intent intent_main_activity = new Intent(PackagesListActivity.this, MainActivity.class);
                intent_main_activity.putExtra(getString(R.string.packageNameKey), packageName);
                startActivity(intent_main_activity);

                Log.v(LOGTAG, "default language packageName = " + packageName);
                localeManager.changeLang(currentLanguage);

            }

        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(PackagesListActivity.this, MenuActivity.class);
        startActivity(intent);
    }
}