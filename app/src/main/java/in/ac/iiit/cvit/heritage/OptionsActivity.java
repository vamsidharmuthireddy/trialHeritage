package in.ac.iiit.cvit.heritage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by HOME on 06-01-2017.
 */

public class OptionsActivity extends AppCompatActivity {


    private static SessionManager sessionManager;
    private static Boolean misfire = false;
    private static int previousPosition = 0;
    private static final String LOGTAG = "OptionsActivity";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_options);


        LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
        localeManager.loadLocale();
        Log.v(LOGTAG,"misfire =  "+misfire.toString());

        setButtonListeners();

    }

    private void setButtonListeners() {

        Button button_instructions = (Button) findViewById(R.id.how_to_use);
        button_instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionsActivity.this, InstructionsActivity.class);
                intent.putExtra(getString(R.string.first_time_instructions), false);
                startActivity(intent);
            }
        });

        Spinner spinner = (Spinner) findViewById(R.id.language_spinner);

        //String sss = sessionManager.getStringSessionPreferences(OptionsActivity.this, getString(R.string.language),"0");


        //Log.v("OptionsActivity", sss);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.available_languages, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(previousPosition);
        //spinner.setSelection(Adapter.NO_SELECTION, false);
        spinner.setPrompt(getString(R.string.select_a_language));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                misfire = !misfire;
                Log.v(LOGTAG, "onItemSelected misfire = " + misfire);
                if (position == 0 && position != previousPosition) {

                    Toast.makeText(parent.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
                    localeManager.saveLocale("en");

                    Log.v(LOGTAG, "Selected English and previousPosition = " + previousPosition);
                    Log.v(LOGTAG, "Selected English and selected position = " + position);
                    previousPosition = position;
                    Intent refresh = new Intent(OptionsActivity.this, OptionsActivity.class);
                    finish();
                    startActivity(refresh);

                } else if (position == 1 && position != previousPosition) {

                    Toast.makeText(parent.getContext(),
                            "మీరు తెలుగు భాషని ఎంచుకున్నారు", Toast.LENGTH_SHORT)
                            .show();
                    LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
                    localeManager.saveLocale("te");

                    Log.v(LOGTAG, "Selected Telugu and previousPosition = " + previousPosition);
                    Log.v(LOGTAG, "Selected Telugu and selected position = " + position);
                    previousPosition = position;
                    Intent refresh = new Intent(OptionsActivity.this, OptionsActivity.class);
                    finish();
                    startActivity(refresh);
                } else if (position == 2 && position != previousPosition) {

                    Toast.makeText(parent.getContext(),
                            "आप ने हिंदी भाषा को चुना है ", Toast.LENGTH_SHORT)
                            .show();
                    LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
                    localeManager.saveLocale("hi");

                    Log.v(LOGTAG, "Selected Hindi and previousPosition = " + previousPosition);
                    Log.v(LOGTAG, "Selected Hindi and selected position = " + position);
                    previousPosition = position;
                    Intent refresh = new Intent(OptionsActivity.this, OptionsActivity.class);
                    finish();
                    startActivity(refresh);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent menuIntent = new Intent(OptionsActivity.this, MenuActivity.class);
        startActivity(menuIntent);


    }
}
