package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class emailVerification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent emailverification_to_otp = new Intent(emailVerification.this, otp.class);
        startActivity(emailverification_to_otp);
        finish();
    }
}