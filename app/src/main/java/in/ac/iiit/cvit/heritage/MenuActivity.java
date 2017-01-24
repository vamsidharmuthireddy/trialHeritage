package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by HOME on 06-01-2017.
 */

public class MenuActivity extends AppCompatActivity {
    /**
     * This method display the menu
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Loading the language preference
        LocaleManager localeManager = new LocaleManager(MenuActivity.this);
        localeManager.loadLocale();
        setContentView(R.layout.activity_menu);

        setButtonClickListeners();


    }

    private void setButtonClickListeners(){
        Button button_mypackages = (Button)findViewById(R.id.view_package);
        button_mypackages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PackagesListActivity.class);
                startActivity(intent);
            }
        });

        Button button_mydownloads = (Button)findViewById(R.id.download_more);
        button_mydownloads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, PackagesDownloaderActivity.class);
                startActivity(intent);
            }
        });


        Button button_options = (Button)findViewById(R.id.options);
        button_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MenuActivity.this, OptionsActivity.class);

                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
