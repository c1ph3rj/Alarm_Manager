package com.c1ph3r.alarm_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.c1ph3r.alarm_manager.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding MAIN;
    MaterialTimePicker timePicker;
    Calendar c;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MAIN = ActivityMainBinding.inflate(getLayoutInflater());
        View view = MAIN.getRoot();
        setContentView(view);

        createNotificationChannel();

        MAIN.setTime.setOnClickListener(onClickSetTime ->{

           createTimePicker();

            timePicker.show(getSupportFragmentManager(), "timePicker");
            timePicker.addOnPositiveButtonClickListener(OnClickOk -> {
               updateTime();
            });
        });

        MAIN.setAlarm.setOnClickListener(onClickSetAlarm -> {
            if (c != null){
                Intent intent = new Intent(MainActivity.this, Alarm.class);
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                Toast.makeText(this, "alarm set successfully.", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "Set the Time first!", Toast.LENGTH_SHORT).show();
        });

        MAIN.cancelAlarm.setOnClickListener(OnClickCancel ->{
            if(pendingIntent != null){
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "alarm canceled successfully.", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(MainActivity.this, "Set an alarm first!", Toast.LENGTH_SHORT).show();
        });

        
    }

    private void updateTime() {
        c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        c.set(Calendar.MINUTE, timePicker.getMinute());
        c.set(Calendar.SECOND, 0);
        String Time = DateFormat.getTimeFormat(this).format(c.getTime());
        MAIN.AlarmTimer.setText(Time);
    }

    private void createTimePicker() {
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(DateFormat.is24HourFormat(MainActivity.this)? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H)
                .setHour((DateFormat.is24HourFormat(MainActivity.this)? 0: 12))
                .setMinute(0)
                .setTitleText("Set Alarm Time")
                .build();
    }


    void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("Alarm", "MY_ALARM", NotificationManager.IMPORTANCE_HIGH);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }

    }
}