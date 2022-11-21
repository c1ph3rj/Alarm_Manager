package com.c1ph3r.alarm_manager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.os.VibratorManager;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class AlarmScreen extends AppCompatActivity {
    // Declaring the required variables.
    Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_screen);



        // To Close The application after one minute.
        new Handler().postDelayed(()->{
            ringtone.stop();
            finishAndRemoveTask();
        },60000);

        // To Display the alarm screen even if the phone is lock.
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        // Fetching the Ringtone from the mobile Phone.
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if (alarmUri == null) {
            // If the ringtone is not available fetch the alarm tone.
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        }

        // setting the fetched ringtone.
        ringtone = RingtoneManager.getRingtone(AlarmScreen.this, alarmUri);

        // To loop the ringtone until the activity ends.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.setLooping(true);

        }

        // play ringtone
        ringtone.play();


        // Button to stop the ringtone and close the activity.
        Button stopButton = findViewById(R.id.stopAlarm);
        stopButton.setOnClickListener(onClickStop ->{
            ringtone.stop();
            finishAndRemoveTask();
        });



    }

}