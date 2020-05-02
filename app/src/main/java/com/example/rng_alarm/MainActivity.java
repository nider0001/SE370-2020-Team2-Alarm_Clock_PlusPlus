package com.example.rng_alarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    //This is
    private Toolbar myToolbar;


    public static Ringtone ringtone;

    private TextView alarmNumber;
    private EditText editNoteText;
    private Button sendNotificationBtn;
    private Button addNewAlarm;
    private TextView textViewer;
    private int launchTimePicker = 1; //request code
    private NotificationHelper mNotificationHelper;
    private static AlarmBank alarmBank = new AlarmBank();
    private TextView texCurrDateTime;

    // Create calendar object, get current date
    Calendar calendar = Calendar.getInstance();
    String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());


    public AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private activeAlarm mCancelAlarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UVBAR
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
        textViewer = findViewById(R.id.textViewer);
        alarmNumber = findViewById(R.id.alarmNum);

        mCancelAlarm = new activeAlarm();


        //waits for user to tap on button
        sendNotificationBtn.setOnClickListener((View v) -> {
            //what happened when the user taps button
            sendNotification(editNoteText.getText().toString());
        });

        //waits for user to tap button
        addNewAlarm.setOnClickListener((View v) -> {
            //what happens when the user taps button
            openAddAlarmActivity();
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }

    public void openAddAlarmActivity() {
        Intent timePickIntent = new Intent(MainActivity.this, addAlarm.class);
        //starts any activity and waits for a result
        //this also calls for the function onActivityResult after it finishes
        startActivityForResult(timePickIntent, launchTimePicker);
    }


    public void sendNotification(String message) {
        NotificationCompat.Builder nb = mNotificationHelper.getChannelNotification(message);
        mNotificationHelper.getManager().notify(1, nb.build());
    }

    //this function gets executed when the startActivityForResult finishes
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == launchTimePicker) {
            //if the activity was completed normally
            if (resultCode == Activity.RESULT_OK) {
                int hour = 0;
                int minute = 0;
//                String note;

                //this grabs the data that was bundle in the addAlarm activity
                hour = data.getIntExtra("HOUR", hour);
                minute = data.getIntExtra("MIN", minute);
//                note = data.getStringExtra("NOTE");

//                String newTime = hour + ":" + minute + "\n" + note;
                String newTime = hour + ":" + minute;
                textViewer.setText(newTime);

//                setAlarm(hour, minute, note);
                setAlarm(hour, minute);
            }
            //if the activity was not completed
            if (resultCode == Activity.RESULT_CANCELED) {
                String noTime = "NO TIME PLACED";
                textViewer.setText(noTime);
            }
        }
    }//onActivityResult

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void setAlarm(int hour, int minute) {
//    private void setAlarm(int hour, int minute, String note) {


        Intent aIntent = new Intent(MainActivity.this, alarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, aIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        Alarm newAlarm = new Alarm();
        newAlarm.setAlarmTime(hour, minute);
//        newAlarm.setAlarmName(note);
        alarmBank.addNewAlarmToBank(newAlarm);

        alarmNumber.setText("Alarm: " + alarmBank.getAlarmBankCount());
    }
    public static AlarmBank getAlarmBank() { return alarmBank; }
}