package com.c1ph3r.alarm_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.TimePicker;

import com.c1ph3r.alarm_manager.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity  {
    private ActivityMainBinding MAIN;
    MaterialTimePicker timePicker;
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

        
    }

    private void updateTime() {
        Calendar c = Calendar.getInstance();
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