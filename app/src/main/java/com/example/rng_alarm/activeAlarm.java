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
import java.util.LinkedList;

public class activeAlarm extends AppCompatActivity {

    private TextView text_alarmName;
    private FloatingActionButton alarmDisable;
    private FloatingActionButton alarmSnooze;
    private AlarmManager alarmManager;
    private TextView texCurrDateTime;
    private Alarm ActiveAlarm;
    private AlarmBank Bank = new AlarmBank();
    private LinkedList<Alarm> List;
    private int requestCode = 0;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_alarm);

        text_alarmName = findViewById(R.id.text_alarmName);
        alarmDisable = findViewById(R.id.btn_disable);
        alarmSnooze = findViewById(R.id.btn_snooze);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        // Get the active alarm
        List = Bank.getActiveAlarmBank();
        ActiveAlarm = List.getFirst();

        requestCode = ActiveAlarm.getId();


        // Create calendar object, get current date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());
        texCurrDateTime = findViewById(R.id.text_currDateTime2);
        //texCurrDateTime.setText(currentDate);
        texCurrDateTime.setText("ID: " + requestCode);

        Intent aIntent = new Intent(activeAlarm.this, alarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(activeAlarm.this,
                requestCode , aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        text_alarmName.setText(Bank.getAlarmByRequestCode(requestCode).getAlarmName());

        /**
         * DEFINITION:  Event driven function disables active alarm
         * PARAMETERS:Intent aIntent = new Intent(activeAlarm.this, alarmReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activeAlarm.this,
                   requestCode , aIntent, PendingIntent.FLAG_UPDATE_CURRENT);
         **/
        alarmDisable.setOnClickListener(v -> {

            //moves alarm back
            List.removeFirst();
            List.addLast(ActiveAlarm);



            alarmManager.cancel(pendingIntent);
            MainActivity.ringtone.stop();



            finish();
        });

        /**
         * DEFINITION: Event driven function snoozes the alarm for 5 additional minutes
         * PARAMETERS:
         **/
        alarmSnooze.setOnClickListener(v -> {

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
