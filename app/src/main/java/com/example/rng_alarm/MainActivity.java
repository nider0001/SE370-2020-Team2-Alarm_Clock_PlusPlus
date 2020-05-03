/*********************************************************************
 *  File            : MainActivity
 *  Created         :
 *  Last Changed/By : 02-May-2020 / Eric Hernandez
 *  Author          : Alex Nider
 *
 *  Purpose:
 *********************************************************************/
package com.example.rng_alarm;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    /****** Private members ******/
    private Toolbar myToolbar;
    private EditText editNoteText;
    private Button sendNotificationBtn;
    private Button addNewAlarm;
    private int launchTimePicker = 1; //request code
    private NotificationHelper mNotificationHelper;
    private TextView texCurrDateTime;
    private PendingIntent pendingIntent;
    private TextView alarm1;
    private TextView alarm2;

    // Create AlarmBank and Alarm objects
    private Alarm NewAlarm;
    private static LinkedList<Alarm> AlarmList = new LinkedList<Alarm>();

    /****** Public members ******/
    public static Ringtone ringtone;
    public AlarmManager alarmManager;

    // Create calendar object, get current date
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UVBAR - Dont now what this is going to be used for...
        myToolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(myToolbar);

        /*
          connects the UI to to variables by ID
         */
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        addNewAlarm = findViewById(R.id.addNewAlarmBtn);
        texCurrDateTime = findViewById(R.id.text_currDateTime);
        texCurrDateTime.setText(currentDate);
        sendNotificationBtn = findViewById(R.id.sendNotificationBtn);
        editNoteText = findViewById(R.id.noteMessage);
        mNotificationHelper = new NotificationHelper(this);

        alarm1 = findViewById(R.id.alarmView1);
        alarm2 = findViewById(R.id.alarmView2);

        /**
         * DEFINITION:  Event driven function sends notifications
         * PARAMETERS:  None
         **/
        sendNotificationBtn.setOnClickListener((View v) -> {
            // What happened when the user taps button
            sendNotification(editNoteText.getText().toString());
        });

        /**
         * DEFINITION:  Event driven function opens add alarm activity
         * PARAMETERS:  None
         **/
        addNewAlarm.setOnClickListener((View v) -> {
            // What happens when the user taps button
            openAddAlarmActivity();
        });
    }

    /**
     * DEFINITION:  Opens the option menu
     * PARAMETERS:  None
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    /**
     * DEFINITION:  Opens activity_add_alarm
     * PARAMETERS:  None
     **/
    public void openAddAlarmActivity() {
        Intent timePickIntent = new Intent(MainActivity.this, addAlarm.class);
        //starts any activity and waits for a result
        //this also calls for the function onActivityResult after it finishes
        startActivityForResult(timePickIntent, launchTimePicker);
    }

    /**
     * DEFINITION:  Sends a notification
     * PARAMETERS:  Message - manually entered message
     **/
    public void sendNotification(String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    /**
     * DEFINITION:  Gets executed when the startActivityForResult finishes
     * PARAMETERS:  Callback
     **/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == launchTimePicker) {
            //if the activity was completed normally
            if (resultCode != Activity.RESULT_OK) {
                // Set the alarm
                setAlarm();
            }
            else {
                // Need to notify user alarm wasn't set
            }
        }
    }//onActivityResult

    /**
     * DEFINITION:  Queues up the next active alarm
     * PARAMETERS:  None
     **/
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm() {

        // Retrieve the active alarm bank and get the first alarm
        AlarmList = AlarmBank.getActiveAlarmBank();
        NewAlarm = AlarmBank.getAlarm();
        AlarmBank.addNewAlarmToBank(NewAlarm);

        int requestCode = NewAlarm.getId();
        int hour = NewAlarm.getAlarmHour();
        int minute = NewAlarm.getAlarmMinutes();

        alarm2.setText("H: " + hour + " M: " + minute + " rc: " + requestCode);
        Intent aIntent = new Intent(MainActivity.this, alarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode,
                aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

}
