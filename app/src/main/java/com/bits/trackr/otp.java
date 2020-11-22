package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class otp extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.verify_otp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true) //Condition to verify OTP
                {
                    Intent otp_to_emailverification = new Intent(otp.this, emailVerification.class);
                    startActivity(otp_to_emailverification);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent otp_to_Register = new Intent(otp.this, Register.class);
        startActivity(otp_to_Register);
        finish();
    }
}