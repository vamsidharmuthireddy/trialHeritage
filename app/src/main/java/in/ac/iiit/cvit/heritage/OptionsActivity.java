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

import java.util.Locale;

/**
 * Created by HOME on 06-01-2017.
 */

public class OptionsActivity extends AppCompatActivity {


    private static final String LOGTAG = "OptionsActivity";
    private static SessionManager sessionManager;
    private static Boolean misfire = false;
    private static int previousPosition = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String prevLanguage = Locale.getDefault().getLanguage();
        Log.v(LOGTAG, "previous Language =  " + prevLanguage);

        LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
        localeManager.loadLocale();

        String currentLanguage = Locale.getDefault().getLanguage();
        Log.v(LOGTAG, "current Language =  " + currentLanguage);

        if (previousPosition == 0 && !currentLanguage.equals(getString(R.string.english))) {
            if (currentLanguage.equals(getString(R.string.telugu))) {
                previousPosition = 1;
            } else if (currentLanguage.equals(getString(R.string.hindi))) {
                previousPosition = 2;
            }

        }

        setContentView(R.layout.activity_options);

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
//                Log.v(LOGTAG, "onItemSelected misfire = " + misfire);
                if (position == 0 && position != previousPosition) {

                    Toast.makeText(parent.getContext(),
                            "You have selected English", Toast.LENGTH_SHORT)
                            .show();
                    LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
                    localeManager.saveLocale(getString(R.string.english));

 //                   Log.v(LOGTAG, "Selected English and previousPosition = " + previousPosition);
   //                 Log.v(LOGTAG, "Selected English and selected position = " + position);
                    previousPosition = position;
                    finish();
                    Intent refresh = new Intent(OptionsActivity.this, OptionsActivity.class);
                    startActivity(refresh);

                } else if (position == 1 && position != previousPosition) {

                    Toast.makeText(parent.getContext(),
                            "మీరు తెలుగు భాషని ఎంచుకున్నారు", Toast.LENGTH_SHORT)
                            .show();
                    LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
                    localeManager.saveLocale(getString(R.string.telugu));

 //                   Log.v(LOGTAG, "Selected Telugu and previousPosition = " + previousPosition);
   //                 Log.v(LOGTAG, "Selected Telugu and selected position = " + position);
                    previousPosition = position;
                    finish();
                    Intent refresh = new Intent(OptionsActivity.this, OptionsActivity.class);
                    startActivity(refresh);
                } else if (position == 2 && position != previousPosition) {

                    Toast.makeText(parent.getContext(),
                            "आप ने हिंदी भाषा को चुना है ", Toast.LENGTH_SHORT)
                            .show();
                    LocaleManager localeManager = new LocaleManager(OptionsActivity.this);
                    localeManager.saveLocale(getString(R.string.hindi));

//                    Log.v(LOGTAG, "Selected Hindi and previousPosition = " + previousPosition);
//                    Log.v(LOGTAG, "Selected Hindi and selected position = " + position);
                    previousPosition = position;
                    finish();
                    Intent refresh = new Intent(OptionsActivity.this, OptionsActivity.class);
                    startActivity(refresh);
                }
                Log.v(LOGTAG, "selected position is " + position);
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
