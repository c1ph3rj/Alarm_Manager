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
    // Declaring the required variables.
    private ActivityMainBinding MAIN;
    MaterialTimePicker timePicker;
    Calendar c;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // View Binding.
        MAIN = ActivityMainBinding.inflate(getLayoutInflater());
        View view = MAIN.getRoot();
        setContentView(view);

        // Creating Notification Channel To display the notification through the channel.
        createNotificationChannel();

        // On Click set Time - show the time picker to pick the time.
        MAIN.setTime.setOnClickListener(onClickSetTime ->{
            // Creating the time Picker to pick the time.
            createTimePicker();
            // Showing the time picker to the user.
            timePicker.show(getSupportFragmentManager(), getString(R.string.timePicker));
            // Get the time from the timepicker and set the value to the textview.
            timePicker.addOnPositiveButtonClickListener(OnClickOk -> updateTime());
        });

        // On Click set Alarm - fetch the time and schedule the alarm based on the time.
        MAIN.setAlarm.setOnClickListener(onClickSetAlarm -> {
            // If the user does not set the time alert the user.
            if (c != null){
                // if the user set the time, get that time and schedule that time to ring the alarm.
                // Creating the intent to schedule the task which sends a broadcast.
                Intent intent = new Intent(MainActivity.this, Alarm.class);
                // Converting the intent to pending intent for alarm Manager.
                pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent, PendingIntent.FLAG_IMMUTABLE);
                // Initializing the alarm manager.
                alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                // Scheduling the alarm manager to the user input time.
                // This sends broadcast to the application when it reaches the exact time.
                alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
                // After setting the  alarm toast a message to the user.
                Toast.makeText(this, R.string.success_Text, Toast.LENGTH_SHORT).show();
            }else
                // Alert the user if the user does not set the time.
                Toast.makeText(this, R.string.AlertTheUser_Text, Toast.LENGTH_SHORT).show();
        });

        // On Click cancel Alarm - get the pending intent and cancel the alarm.
        MAIN.cancelAlarm.setOnClickListener(OnClickCancel ->{
            if(pendingIntent != null){
                // Canceling the scheduled alarm.
                alarmManager.cancel(pendingIntent);
                // Toast the message to the user.
                Toast.makeText(this, R.string.Canceled_Text, Toast.LENGTH_SHORT).show();
                pendingIntent = null;
            }else
                // Toast the message if there is no scheduled alarm.
                Toast.makeText(MainActivity.this, R.string.AlertTheUser2_Text, Toast.LENGTH_SHORT).show();
        });

        
    }

    // Method to update the time picker value to the textview.
    private void updateTime() {
        // Initializing calendar variable.
        c = Calendar.getInstance();
        // Setting the calendar object with the time picker hour and minute and seconds.
        c.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
        c.set(Calendar.MINUTE, timePicker.getMinute());
        c.set(Calendar.SECOND, 0);
        // Converting the calendar object time to user understandable string.
        String Time = DateFormat.getTimeFormat(this).format(c.getTime());
        // Setting the occurred time to the textview.
        MAIN.AlarmTimer.setText(Time);
    }

    // Method to create a Timepicker.
    private void createTimePicker() {
        // Initializing MaterialTimePicker.
        timePicker = new MaterialTimePicker.Builder()
                .setTimeFormat(DateFormat.is24HourFormat(MainActivity.this)? TimeFormat.CLOCK_24H : TimeFormat.CLOCK_12H)
                .setHour((DateFormat.is24HourFormat(MainActivity.this)? 0: 12))
                .setMinute(0)
                .setTitleText(R.string.SetAlarmTime_Text)
                .build();
    }


    // Method to create a notification channel which act as a bridge.
    void createNotificationChannel(){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            // Creating Notification Channel.
            NotificationChannel notificationChannel = new NotificationChannel(getString(R.string.Alarm), getString(R.string.MyAlarm), NotificationManager.IMPORTANCE_HIGH);
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            notificationManagerCompat.createNotificationChannel(notificationChannel);
        }

    }
}