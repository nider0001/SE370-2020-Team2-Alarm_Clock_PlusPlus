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

import java.text.DateFormat;
import java.util.Calendar;

public class activeAlarm extends AppCompatActivity {

    private TextView timeDisplay;
    private TextView noteDisplay;
    private FloatingActionButton alarmDisable;
    private FloatingActionButton alarmSnooze;
    private AlarmManager alarmManager;
    private TextView texCurrDateTime;
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

        // Create calendar object, get current date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        texCurrDateTime = findViewById(R.id.text_currDateTime2);
        texCurrDateTime.setText(currentDate);

        timeDisplay.setText(MainActivity.getAlarmBank().getAlarm(0).getAlarmHour() + ":" + MainActivity.getAlarmBank().getAlarm(0).getAlarmMinutes());
        noteDisplay.setText(MainActivity.getAlarmBank().getAlarm(0).getAlarmName());

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

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, (MainActivity.getAlarmBank().getAlarm(0).getAlarmHour() + 5));
            calendar2.set(Calendar.MINUTE, MainActivity.getAlarmBank().getAlarm(0).getAlarmMinutes());
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pendingIntent);

            MainActivity.ringtone.stop();
            finish();
        });
    }
}
