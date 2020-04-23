package com.example.rng_alarm;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.rng_alarm.R;

public class NotificationHelper extends ContextWrapper {
    /**
     * variables that define ID and name of the message
     *
     * NotificationManager is an object to tell user what is happening in the background
     */
    public static final String sendNoteID = "sendNoteID";
    public static final String sendNoteName = "Send Note";
    private NotificationManager mManager;

    /**
     * Default constructor
     */
    public NotificationHelper(Context base) {
        super(base);
        /**
         * Checks if the version is compatible with application
         */
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        /**
         * NotificationChannel - a collection of settings to be applied to multiple notifications
         *  
         */
        NotificationChannel channel1 = new NotificationChannel(sendNoteID, sendNoteName, NotificationManager.IMPORTANCE_DEFAULT);
        //enable if notification light comes on
        channel1.enableLights(true);
        //enable if notification vibration
        channel1.enableVibration(true);
        //set notification light color
        channel1.setLightColor(R.color.colorPrimary);
        //set the priority of the notification
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        //creates the notification channel to be sent
        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager() {
        //if the notification manager is empty them send the notification info
        if(mManager == null) {
            mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * This function helps build the notification to be sent
     *
     * @param message takes in the type of message for the notification
     */
    public NotificationCompat.Builder getChannelNotification(String message) {
        return new NotificationCompat.Builder(getApplicationContext(), sendNoteID)
                .setContentTitle("Alarmy")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_note);
    }
}
