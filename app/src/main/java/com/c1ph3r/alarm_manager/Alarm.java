package com.c1ph3r.alarm_manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // Intent to Open activity when broadcast received.
        Intent newIntent = new Intent(context, AlarmScreen.class);
        // Setting the intent as a new task.
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // When broadcast receive start the activity.
        context.startActivity(newIntent);

        // Creating New Notification when Broadcast is Received.
        NotificationCompat.Builder Notification = new NotificationCompat.Builder(context, context.getString(R.string.Alarm))
                .setContentTitle(context.getString(R.string.MyAlarm_text))
                .setContentText(context.getString(R.string.Ringing_Text))
                .setSmallIcon(R.drawable.alarm_ic)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        // Sending the Notification to the user.
        NotificationManagerCompat sendNotification = NotificationManagerCompat.from(context);
        sendNotification.notify(1 , Notification.build());

    }
}
