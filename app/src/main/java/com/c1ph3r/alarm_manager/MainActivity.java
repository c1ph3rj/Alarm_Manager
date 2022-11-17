package com.c1ph3r.alarm_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MAIN = ActivityMainBinding.inflate(getLayoutInflater());
        View view = MAIN.getRoot();
        setContentView(view);

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
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) MainActivity.this.getSystemService(Context.ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
            }else
                Toast.makeText(this, "Set the Time first!", Toast.LENGTH_SHORT).show();
        });

        MAIN.cancelAlarm.setOnClickListener(OnClickCancel ->{

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
}