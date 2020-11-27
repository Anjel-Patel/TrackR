package com.bits.trackr;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private static int SplashTime = 1000;
    private String login_state;

    @Override
    protected void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);
        this.login_state = prefs.getString("login_state", "0");
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run() {
//                Toast.makeText(getBaseContext(), login_state, Toast.LENGTH_SHORT).show();
                switch (login_state) {
                    case "1": {
//                        Toast.makeText(getBaseContext(), "Should go to dashboard", Toast.LENGTH_SHORT).show();
                        Intent Splash_toDashboard = new Intent(SplashActivity.this, dashboard.class);
                        startActivity(Splash_toDashboard);
                        finish();
                        break;
                    }
                    default: {
                        Intent Splash_toRegister = new Intent(SplashActivity.this, Register.class);
                        startActivity(Splash_toRegister);
                        finish();
                    }
                }
            }
        }, SplashTime);
    }
}
