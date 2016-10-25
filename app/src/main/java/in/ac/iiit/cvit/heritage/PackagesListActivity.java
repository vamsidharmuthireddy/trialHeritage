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

public class PackagesListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button button_download_packages;
    private ListView listview_package_list;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_lists);

        sessionManager = new SessionManager();

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.my_heritage_sites);
        setSupportActionBar(toolbar);

        button_download_packages = (Button) findViewById(R.id.button_download_packages);
        button_download_packages.setText(R.string.download_packages);
        button_download_packages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_package_downloader = new Intent(PackagesListActivity.this, PackagesDownloaderActivity.class);
                startActivity(intent_package_downloader);
            }
        });

        //temporary hard coding
        String[] packages = {"Golconda", "Hampi"};
        listview_package_list = (ListView) findViewById(R.id.listview_package_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PackagesListActivity.this, android.R.layout.simple_list_item_1, packages);
        listview_package_list.setAdapter(adapter);
        listview_package_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                String packageName = (String) textView.getText();
                sessionManager.setSessionPreferences(PackagesListActivity.this, "package_name", packageName);

                Intent intent_main_activity = new Intent(PackagesListActivity.this, MainActivity.class);
                intent_main_activity.putExtra("package", packageName);
                startActivity(intent_main_activity);
            }
        });
    }
}
