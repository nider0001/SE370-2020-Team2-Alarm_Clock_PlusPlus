package com.example.myapplication;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {
    public static final String sendNoteID = "sendNoteID";
    public static final String sendNoteName = "Send Note";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel channel1 = new NotificationChannel(sendNoteID, sendNoteName, NotificationManager.IMPORTANCE_HIGH);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        getManager().createNotificationChannel(channel1);
    }

    public NotificationManager getManager()
    {
        if(mManager == null) {
            mManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }

        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String message) {
        return new NotificationCompat.Builder(getApplicationContext(), sendNoteID)
                .setContentTitle("Alarmy")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_note);
    }
}
