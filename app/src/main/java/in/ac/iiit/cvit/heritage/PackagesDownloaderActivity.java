package in.ac.iiit.cvit.heritage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class PackagesDownloaderActivity extends AppCompatActivity {
    /**
     * This class displays the packages available for download. Upon click, downloads the relevant package
     */
    private ListView listview_available_packages;
    private Toolbar toolbar;

    private static final int PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 3;
    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_downloader);

        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setTitle(R.string.available_for_download);
        setSupportActionBar(toolbar);

        //temporary hard coding
       // String[] packages = {"Golconda", "Hampi"};
        String[] packages = getResources().getStringArray(R.array.download_packages);
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(PackagesDownloaderActivity.this, android.R.layout.simple_list_item_1, packages);

        listview_available_packages = (ListView) findViewById(R.id.listview_available_packages);
        //listview_available_packages.setAdapter(new PackagesDownloaderAdapter(PackagesDownloaderActivity.this, packages));

        listview_available_packages.setAdapter(adapter);
        listview_available_packages.setClickable(true);
        listview_available_packages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //downloading the selected package
                String list_item = adapter.getItem(position).toLowerCase();

                new AlertDialog.Builder(PackagesDownloaderActivity.this)
                        .setMessage("Do you want to download the " + list_item + " package?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {

                                String list_item_copy = adapter.getItem(position).toLowerCase();
                                new PackageDownloader(PackagesDownloaderActivity.this).execute(list_item_copy);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {

                            // do something when the button is clicked
                            public void onClick(DialogInterface arg0, int arg1) {
                                //onBackPressed();
                            }
                        })
                        .show();


            }
        });

        ActivityCompat.requestPermissions(PackagesDownloaderActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
            case PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(PackagesDownloaderActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
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
        intent_permissions.setData(Uri.parse("package:" + PackagesDownloaderActivity.this.getPackageName()));

        intent_permissions.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent_permissions.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent_permissions.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

        PackagesDownloaderActivity.this.startActivity(intent_permissions);
    }

}
