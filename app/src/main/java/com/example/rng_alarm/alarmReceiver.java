/*********************************************************************
 *  File            : alarmReceiver
 *  Created         :
 *  Last Changed/By : 02-May-2020 / Eric Hernandez
 *  Author          : Alex Nider
 *
 *  Purpose:
 *********************************************************************/
package com.example.rng_alarm;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

public class alarmReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // This file receives the alarm and sets it off
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        MainActivity.ringtone = RingtoneManager.getRingtone(context, uri);
        MainActivity.ringtone.play();

        Intent activeAlarmIntent = new Intent();
        activeAlarmIntent.setClassName("com.example.rng_alarm",
                "com.example.rng_alarm.activeAlarm");
        activeAlarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(activeAlarmIntent);

    }
}
