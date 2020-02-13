package com.example.myapplication;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private EditText editNoteText;
    private Button sendNotificationBtn;
    private Button addNewAlarm;

    private NotificationHelper mNotificationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNewAlarm = findViewById(R.id.addNewAlarmBtn);
        sendNotificationBtn = findViewById(R.id.sendNotificationBtn);
        editNoteText = findViewById(R.id.noteMessage);
        mNotificationHelper = new NotificationHelper(this);

        sendNotificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification(editNoteText.getText().toString());
            }
        });


        addNewAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment alarmPicker = new alarmPicker();

                alarmPicker.show(getSupportFragmentManager(), "time picker");
            }
        });
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
