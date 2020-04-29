package com.example.rng_alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class activeAlarm extends AppCompatActivity {

    private TextView timeDisplay;
    private TextView noteDisplay;
    private FloatingActionButton alarmDisable;
    private FloatingActionButton alarmSnooze;
    private AlarmManager alarmManager;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);

        timeDisplay = findViewById(R.id.alarmTimeDisplay);
        noteDisplay = findViewById(R.id.alarmNoteDisplay);
        alarmDisable = findViewById(R.id.alarmDisable);
        alarmSnooze = findViewById(R.id.alarmSnooze);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        timeDisplay.setText(addAlarm.getHour() + ":" + addAlarm.getMin());
        noteDisplay.setText(addAlarm.getNote());

        alarmDisable.setOnClickListener(v -> {
            Intent aIntent = new Intent(activeAlarm.this, alarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activeAlarm.this, 0, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
            MainActivity.ringtone.stop();
            finish();
        });
        alarmSnooze.setOnClickListener(v -> {
            Intent aIntent = new Intent(activeAlarm.this, alarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activeAlarm.this, 0, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, (addAlarm.getHour() + 300000));
            calendar.set(Calendar.MINUTE, addAlarm.getMin());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

            MainActivity.ringtone.stop();
            finish();
        });
    }
}
