package com.c1ph3r.alarm_manager;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;


public class Alarm extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, AlarmScreen.class);
        newIntent.putExtra("Intent", intent);
        newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,0,newIntent, PendingIntent.FLAG_IMMUTABLE);
        context.startActivity(newIntent);

        NotificationCompat.Builder Notification = new NotificationCompat.Builder(context, "Alarm")
                .setContentTitle("My Alarm")
                .setContentText("Alarm is Ringing ...")
                .setSmallIcon(R.drawable.alarm_ic)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        NotificationManagerCompat sendNotification = NotificationManagerCompat.from(context);
        sendNotification.notify(1 , Notification.build());

    }
}
