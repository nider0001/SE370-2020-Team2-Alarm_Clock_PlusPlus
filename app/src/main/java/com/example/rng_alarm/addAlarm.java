/*********************************************************************
 *  File            : addAlarm
 *  Created         :
 *  Last Changed/By : 02-May-2020 / Eric Hernandez
 *  Author          : Alex Nider
 *
 *  Purpose:
 *********************************************************************/
package com.example.rng_alarm;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class addAlarm extends AppCompatActivity {
    private Button addButton;
    private TimePicker timePicker;
    private EditText alarmName;
    private static String name;
    private static int hour;
    private static int min;

    // Create new alarm object
    private Alarm NewAlarm = new Alarm();
    private static int defaultNameCount = 1;

    // Static alarmBank to store each alarm
    private static AlarmBank Bank = new AlarmBank();


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
                setAlarmAttributes(name,hour,min);

                // Send to bank
                Bank.addNewAlarmToBank(NewAlarm);

                finish();
            }
        });
    }

    /**
     * DEFINITION:  Sets alarm attributes.
     * PARAMETERS:  Name, hour, minutes
     **/
     void setAlarmAttributes(String name, int hour, int min) {
         NewAlarm.setAlarmName(name);
         NewAlarm.setAlarmTime(hour, min);
     }
}