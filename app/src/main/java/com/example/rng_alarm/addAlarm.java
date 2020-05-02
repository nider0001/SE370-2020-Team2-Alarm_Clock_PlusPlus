package com.example.rng_alarm;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class addAlarm extends AppCompatActivity {
    private Button addButton;
    private TimePicker timePicker;
    private EditText alarmNote;
    private static String note;
    private static int hour;
    private static int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);

        addButton = findViewById(R.id.buttonAdd);
        timePicker = findViewById(R.id.timePicker1);
        timePicker = findViewById(R.id.timePicker1);
        alarmNote = findViewById(R.id.noteAlarm);
        note = " ";
        hour = 0;
        min = 0;


        addButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                //get time from the timePicker
                hour = timePicker.getHour();
                min = timePicker.getMinute();

                note = alarmNote.getText().toString();

                if(note.length() <= 0) {
                    note = " ";
                }


                //creates a bundle to send back to main activity
                Bundle timeSet = new Bundle();
                timeSet.putInt("HOUR", hour);
                timeSet.putInt("MIN", min);

                timeSet.putString("NOTE", note);

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