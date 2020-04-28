package com.example.rng_alarm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


// Need for date and time
import java.util.Calendar;
import java.text.DateFormat;

public class MainActivity extends AppCompatActivity {

    private Button addNewAlarm;
    private TextView texCurrDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create calendar object, get current date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(calendar.getTime());

        /**
         * connects the UI to to variables by ID
         **/
        addNewAlarm = findViewById(R.id.btn_createAlarm);
        texCurrDateTime = findViewById(R.id.text_currDateTime);
        texCurrDateTime.setText(currentDate);


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
        MainActivity.this.startActivity(timePickIntent);
    }

}
