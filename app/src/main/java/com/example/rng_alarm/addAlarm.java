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

//    private Alarm NewAlarm = new Alarm();
    private static int defaultNameCount = 1;

    // Static alarmBank to store each alarm
//    private static AlarmBank Bank = new AlarmBank();


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
//                setAlarmAttributes(name,hour,min);

                // Send to bank
//                Bank.addNewAlarmToBank(NewAlarm);

                //creates a bundle to send back to main activity
                Bundle timeSet = new Bundle();
                timeSet.putInt("HOUR", hour);
                timeSet.putInt("MIN", min);

                timeSet.putString("NOTE", name);

                //creates intent to send back to main activity
                Intent returnIntent = new Intent();
                //adds the info form bundle into the intent
                returnIntent.putExtras(timeSet);

                //sets the type of result when the activity is returned
                setResult(RESULT_OK, returnIntent);
                //finish the activity
                finish();
            }
        });

    }

    /**
     * DEFINITION:  Sets alarm attributes.
     * PARAMETERS:  Name, hour, minutes
     **/
     void setAlarmAttributes(String name, int hour, int min) {
//         NewAlarm.setAlarmName(name);name
//         NewAlarm.setAlarmTime(hour, min);
     }
}