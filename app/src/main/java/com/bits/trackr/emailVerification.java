package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

public class emailVerification extends AppCompatActivity {
    TextView verification_waiting;
    ConstraintLayout Curr_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
        verification_waiting = findViewById(R.id.email_verification_waiting);
        Curr_layout = (ConstraintLayout)findViewById(R.id.email_verification_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                if(true) //Check if email is verified.
                {
                    Intent emailverification_to_dashboard = new Intent(emailVerification.this, dashboard.class);
                    startActivity(emailverification_to_dashboard);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent emailverification_to_otp = new Intent(emailVerification.this, otp.class);
        startActivity(emailverification_to_otp);
        finish();
    }
}