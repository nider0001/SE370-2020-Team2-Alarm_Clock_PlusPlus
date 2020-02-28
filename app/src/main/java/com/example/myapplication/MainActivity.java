package com.example.myapplication;

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

    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * connects the UI to to variables by ID
         */
        addNewAlarm = findViewById(R.id.addNewAlarmBtn);
        sendNotificationBtn = findViewById(R.id.sendNotificationBtn);
        editNoteText = findViewById(R.id.noteMessage);
        mNotificationHelper = new NotificationHelper(this);

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
        startActivity(timePickIntent);
    }


    public void sendNotification(String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        TextView textView = findViewById(R.id.textViewer);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);
    }
}
