package com.example.myapplication;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private EditText editNoteText;
    private Button sendNotificationBtn;
    private Button addNewAlarm;
    private TextView textViewer;
    private int launchTimePicker = 1; //request code

    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * connects the UI to to variables by ID
         **/
        addNewAlarm = findViewById(R.id.addNewAlarmBtn);
        sendNotificationBtn = findViewById(R.id.sendNotificationBtn);
        editNoteText = findViewById(R.id.noteMessage);
        mNotificationHelper = new NotificationHelper(this);
        textViewer = findViewById(R.id.textViewer);

        //waits for user to tap on button
        sendNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what happened when the user taps button
                sendNotification(editNoteText.getText().toString());
            }
        });

        //waits for user to tap button
        addNewAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //what happens when the user taps button
                openAddAlarmActivity();
            }
        });
    }

    public void openAddAlarmActivity() {
        Intent timePickIntent = new Intent(MainActivity.this, addAlarm.class);
        //starts any activity and waits for a result
        //this also calls for the function onActivityResult after it finishes
        startActivityForResult(timePickIntent,launchTimePicker);
    }


    public void sendNotification(String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = findViewById(R.id.textViewer);
        String newTime = "Hour: " + hourOfDay + " Minute: " + minute;
        textView.setText(newTime);
    }

    //this function gets executed when the startActivityForResult finishes
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == launchTimePicker) {
            //if the activity was completed normally
            if(resultCode == Activity.RESULT_OK){
                int hour = 0;
                int minute = 0;
                //this grabs the data that was bundle in the addAlarm activity
                hour = data.getIntExtra("HOUR", hour);
                minute = data.getIntExtra("MIN", minute);
                String newTime = hour + ":" + minute;
                textViewer.setText(newTime);
            }
            //if the activity was not completed
            if (resultCode == Activity.RESULT_CANCELED) {
                String noTime = "NO TIME PLACED";
                textViewer.setText(noTime);
            }
        }
    }//onActivityResult
}
