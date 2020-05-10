/*********************************************************************
 *  File            : addAlarm
 *  Created         :
 *  Last Changed/By : 02-May-2020 / Eric Hernandez
 *  Author          : Alex Nider
 *
 *  Purpose:
 *********************************************************************/
package rng.AlarmManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rng_alarm.R;

import rng.AlarmBankManager.AlarmBank;

public class addAlarm extends AppCompatActivity {
    private Button addButton;
    private TimePicker timePicker;
    private EditText alarmName;
    private static String name;
    private static int hour;
    private static int min;

    // Create new alarm object
    private Alarm NewAlarm = new Alarm();
    private AlarmBank Bank = new AlarmBank();
    private static int defaultNameCount = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        addButton = findViewById(R.id.buttonAdd);
        timePicker = findViewById(R.id.timePicker1);
        timePicker = findViewById(R.id.timePicker1);
        alarmName = findViewById(R.id.text_alarmName);
        name = " ";
        hour = 0;
        min = 0;

        /**
         * DEFINITION:  Event driven function creates a new alarm and stores in bank
         * PARAMETERS:  None
         **/
        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                // Get Alarm name and time
                hour = timePicker.getHour();
                min = timePicker.getMinute();
                name = alarmName.getText().toString();

                // If the user has not defined a name, assign default
                if(name.length() <= 0) {
                    name = "Alarm" + defaultNameCount;
                    defaultNameCount++;
                }

                // Set all alarm attributes
                NewAlarm = new Alarm(hour, min, name);

                // Send to bank
                Bank.addNewAlarmToBank(NewAlarm);

                finish();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}