/*********************************************************************
 *  File            : activeAlarm
 *  Created         :
 *  Last Changed/By : 02-May-2020 / Eric Hernandez
 *  Author          : Alex Nider
 *
 *  Purpose:
 *********************************************************************/
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
    private TextView text_alarmName;
    private FloatingActionButton alarmDisable;
    private FloatingActionButton alarmSnooze;
    private AlarmManager alarmManager;
    private TextView texCurrDateTime;
    private Alarm ActiveAlarm = new Alarm();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);

        timeDisplay = findViewById(R.id.alarmTimeDisplay);
        text_alarmName = findViewById(R.id.text_alarmName);
        alarmDisable = findViewById(R.id.btn_disable);
        alarmSnooze = findViewById(R.id.btn_snooze);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Get the active alarm
        ActiveAlarm = MainActivity.getActiveAlarm();

        // Create calendar object, get current date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        texCurrDateTime = findViewById(R.id.text_currDateTime2);
        texCurrDateTime.setText(currentDate);

        timeDisplay.setText(ActiveAlarm.getAlarmHour() + ":" + ActiveAlarm.getAlarmMinutes());
        text_alarmName.setText(ActiveAlarm.getAlarmName());

        /**
         * DEFINITION:  Event driven function disables active alarm
         * PARAMETERS:
         **/
        alarmDisable.setOnClickListener(v -> {
            Intent aIntent = new Intent(activeAlarm.this, alarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activeAlarm.this,
                    0, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            alarmManager.cancel(pendingIntent);
            MainActivity.ringtone.stop();
            finish();
        });

        /**
         * DEFINITION: Event driven function snoozes the alarm for 5 additional minutes
         * PARAMETERS:
         **/
        alarmSnooze.setOnClickListener(v -> {
            Intent aIntent = new Intent(activeAlarm.this, alarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activeAlarm.this,
                    0, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, (ActiveAlarm.getAlarmHour()));
            calendar2.set(Calendar.MINUTE, (ActiveAlarm.getAlarmMinutes() + 5));
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(),
                    pendingIntent);

            MainActivity.ringtone.stop();
            finish();
        });
    }
}
