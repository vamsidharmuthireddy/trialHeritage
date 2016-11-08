package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * Created by HOME on 03-11-2016.
 */

public class InstructionsActivity extends AppCompatActivity {
    /**
     * This class provides the instruction for app uasge to the user
     */

    private static final int SLEEP = 5;
    private static final String LOGTAG = "InstructionsActivity";
    private Boolean first_time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_instructions);

        first_time = getIntent().getExtras().getBoolean(getString(R.string.first_time_instructions));
//        Log.v(LOGTAG, "first_time = " + first_time);
        try {

            if (first_time) {
                //This activity is only shown when opened for the first time after installing
//                Log.v(LOGTAG, "opened Instructions activity for the first time");

                setContentView(R.layout.activity_instructions);
                Button done_with_instructions = (Button) findViewById(R.id.done_with_instructions);

                done_with_instructions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_package_list = new Intent(InstructionsActivity.this, PackagesListActivity.class);
                        intent_package_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent_package_list);
                        finish();
                    }

                });


            } else {

                //From second time whenever this app is opened, this activity is shown
 //               Log.v(LOGTAG, "opened Instructions activity not for the first time");

                setContentView(R.layout.activity_instructions);
                Button done_with_instructions = (Button) findViewById(R.id.done_with_instructions);
                done_with_instructions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onBackPressed();
                    }
                });
            }
        } catch (Exception e) {
            Log.e(LOGTAG, e.toString());
        }


    }

    @Override
    public void onBackPressed() {
        if (first_time) {
            Intent intent_package_list = new Intent(InstructionsActivity.this, PackagesListActivity.class);
            intent_package_list.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent_package_list);
            finish();
        } else {

            super.onBackPressed();
        }

    }


}

