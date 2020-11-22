package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Register extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true) //Replace with conditions for user authentication
                {
                    Intent Register_to_otp = new Intent(Register.this, otp.class);
                    startActivity(Register_to_otp);
                    finish();
                }
            }
        });
    }
}