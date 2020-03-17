package com.example.myapplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class addAlarm extends AppCompatActivity {
    private Button addButton;
    private TimePicker timePicker;
    private TextView currentTime;
    private TextView alarmTime;
    private int hr;
    private int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        addButton = findViewById(R.id.buttonAdd);
        currentTime = findViewById(R.id.curTime);
        timePicker = findViewById(R.id.timePicker1);
        alarmTime = findViewById(R.id.displayAlarmTime);

        Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        currentTime.setText(hour + ":" + minute);
        alarmTime.setText("--:--");

        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //get time from the timePicker
                hr = timePicker.getHour();
                min = timePicker.getMinute();

                //creates a bundle to send back to main activity
                Bundle timeSet = new Bundle();
                timeSet.putInt("HOUR", hr);
                timeSet.putInt("MIN", min);

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
}
