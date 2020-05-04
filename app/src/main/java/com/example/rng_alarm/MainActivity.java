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
import android.widget.CompoundButton;
import android.widget.Switch;
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
    private Button addNewAlarm;
    private int launchTimePicker = 1;
    private NotificationHelper mNotificationHelper;
    private TextView texCurrDateTime;
    private PendingIntent pendingIntent;

    // Create AlarmBank and Alarm objects
    private static AlarmBank Bank = new AlarmBank();
    private Alarm NewAlarm;
    private static LinkedList<Alarm> AlarmList = new LinkedList<Alarm>();

    /****** Public members ******/
    public static Ringtone ringtone;
    public AlarmManager alarmManager;

    // Create calendar object, get current date
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UVBAR
        myToolbar=findViewById(R.id.toolbarID);
        setSupportActionBar(myToolbar);

        /*
          connects the UI to to variables by ID
         */
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        addNewAlarm = findViewById(R.id.addNewAlarmBtn);
        texCurrDateTime = findViewById(R.id.text_currDateTime);
        texCurrDateTime.setText(currentDate);
        mNotificationHelper = new NotificationHelper(this);


        /**
         * DEFINITION:  Event driven function opens add alarm activity
         * PARAMETERS:  None
         **/
        addNewAlarm.setOnClickListener((View v) -> {
            // What happens when the user taps button
            openAddAlarmActivity();
        });

        /**
         * DEFINITION:  Event driven function toggles alarm
         * PARAMETERS:  None
         **/
        CompoundButton.OnCheckedChangeListener multiListener = new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton v, boolean isChecked) {
                int id;
                NewAlarm = new Alarm();
                // Determine alarm to toggle
                switch (v.getId()){
                    case R.id.switch_Alarm1:
                        NewAlarm = AlarmList.get(0);
                        break;
                    case R.id.switch_Alarm2:
                        NewAlarm = AlarmList.get(1);
                        break;
                    case R.id.switch_Alarm3:
                        NewAlarm = AlarmList.get(2);
                        break;
                    case R.id.switch_Alarm4:
                        NewAlarm = AlarmList.get(3);
                        break;
                    case R.id.switch_Alarm5:
                        NewAlarm = AlarmList.get(4);
                        break;
                    case R.id.switch_Alarm6:
                        NewAlarm = AlarmList.get(5);
                        break;
                    case R.id.switch_Alarm7:
                        NewAlarm = AlarmList.get(6);
                        break;
                    case R.id.switch_Alarm8:
                        NewAlarm = AlarmList.get(7);
                        break;
                    case R.id.switch_Alarm9:
                        NewAlarm = AlarmList.get(8);
                        break;
                    case R.id.switch_Alarm10:
                        NewAlarm = AlarmList.get(10);
                        break;
                }

                // Toggle selected alarm
                NewAlarm.toggleAlarm();
                id = NewAlarm.getId();

                // Toggle intent
                if (NewAlarm.getAlarmActiveStatus() == false) {
                    disableIntent(id);
                }
                else {
                    enableIntent(NewAlarm, id);
                }

                // Move alarm between active and inactive list
                Bank.moveAlarm(NewAlarm, id);
            }
        };

        // On each switch
        ((Switch) findViewById(R.id.switch_Alarm1)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm2)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm3)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm4)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm5)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm6)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm7)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm8)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm9)).setOnCheckedChangeListener(multiListener);
        ((Switch) findViewById(R.id.switch_Alarm10)).setOnCheckedChangeListener(multiListener);
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
                // Set up list of alarms
                displayAlarms();
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

        // Create new alarm object and last object in bank
        NewAlarm = new Alarm();
        NewAlarm = Bank.getAlarm();

        int requestCode = NewAlarm.getId();
        int hour = NewAlarm.getAlarmHour();
        int minute = NewAlarm.getAlarmMinutes();

        Intent aIntent = new Intent(MainActivity.this, alarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, requestCode,
                aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    /**
     * DEFINITION:  Sets up switches
     * PARAMETERS:  None
     **/
    private void displayAlarms() {
        NewAlarm = new Alarm();
        AlarmList = Bank.getAlarmBank();
        String switchName = "";
        Boolean status = true;
        int id;

        for (int i = 0; i < AlarmList.size(); i++) {
            NewAlarm = AlarmList.get(i);
            switchName = NewAlarm.getAlarmName();
            id = NewAlarm.getId();
            status = NewAlarm.getAlarmActiveStatus();
            makeSwitchVisible(i + 1, switchName, id, status);
        }
    }

    /**
     * DEFINITION:  Sets up switches
     * PARAMETERS:  None
     **/
    private void makeSwitchVisible(int switchNum, String switchName, int id, boolean status) {

        switch (switchNum) {
            case 1:
                Switch alarm1 = findViewById(R.id.switch_Alarm1);
                alarm1.setText(id + ":" + switchName + ":" + status);
                alarm1.setVisibility(View.VISIBLE);
                alarm1.setChecked(status);
                break;
            case 2:
                Switch alarm2 = findViewById(R.id.switch_Alarm2);
                alarm2.setText(id + ":" + switchName + ":" + status);
                alarm2.setVisibility(View.VISIBLE);
                alarm2.setChecked(status);
                break;
            case 3:
                Switch alarm3 = findViewById(R.id.switch_Alarm3);
                alarm3.setText(id + ":" + switchName + ":" + status);
                alarm3.setVisibility(View.VISIBLE);
                alarm3.setChecked(status);
                break;
            case 4:
                Switch alarm4 = findViewById(R.id.switch_Alarm4);
                alarm4.setText(id + ":" + switchName + ":" + status);
                alarm4.setVisibility(View.VISIBLE);
                alarm4.setChecked(status);
                break;
            case 5:
                Switch alarm5 = findViewById(R.id.switch_Alarm5);
                alarm5.setText(id + ":" + switchName + ":" + status);
                alarm5.setVisibility(View.VISIBLE);
                alarm5.setChecked(status);
                break;
            case 6:
                Switch alarm6 = findViewById(R.id.switch_Alarm6);
                alarm6.setText(id + ":" + switchName + ":" + status);
                alarm6.setVisibility(View.VISIBLE);
                alarm6.setChecked(status);
                break;
            case 7:
                Switch alarm7 = findViewById(R.id.switch_Alarm7);
                alarm7.setText(id + ":" + switchName + ":" + status);
                alarm7.setVisibility(View.VISIBLE);
                alarm7.setChecked(status);
                break;
            case 8:
                Switch alarm8 = findViewById(R.id.switch_Alarm8);
                alarm8.setText(id + ":" + switchName + ":" + status);
                alarm8.setVisibility(View.VISIBLE);
                alarm8.setChecked(status);
                break;
            case 9:
                Switch alarm9 = findViewById(R.id.switch_Alarm9);
                alarm9.setText(id + ":" + switchName + ":" + status);
                alarm9.setVisibility(View.VISIBLE);
                alarm9.setChecked(status);
                break;
            case 10:
                Switch alarm10 = findViewById(R.id.switch_Alarm10);
                alarm10.setText(id + ":" + switchName + ":" + status);
                alarm10.setVisibility(View.VISIBLE);
                alarm10.setChecked(status);
                break;
        }
    }

    /**
     * DEFINITION:  Disables alarm intent
     * PARAMETERS:
     **/
    public void disableIntent(int requestCode) {
        Intent aIntent = new Intent(MainActivity.this, alarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                requestCode , aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    /**
     * DEFINITION:  Enables alarm intent
     * PARAMETERS:
     **/
    public void enableIntent(Alarm alarm, int requestCode) {
        Intent aIntent = new Intent(MainActivity.this, alarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,
                requestCode , aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        int hour = NewAlarm.getAlarmHour();
        int minute = NewAlarm.getAlarmMinutes();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

}
