package com.randomone.androidmonsterc3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.UUID;

import static com.randomone.androidmonsterc3.StudentProfileEdit.SHARED_PREFS;

public class SplashScreenActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    private static String uniqueID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        //setting device UUID
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if(sharedPreferences.getBoolean("firstTime", true)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            uniqueID = UUID.randomUUID().toString();
            editor.putBoolean("firstTime", false);
            editor.putString("deviceID", uniqueID);
            editor.commit();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(mainIntent);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }
}