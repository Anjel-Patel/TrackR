package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class Login extends Activity {
    private Button login_phone;
    private Button login_email;
    private Button login_google;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_phone = (Button)findViewById(R.id.LoginPhone);
        login_email = (Button)findViewById(R.id.LoginEmail);
        login_google = (Button)findViewById(R.id.LoginGoogle);
    }

    @Override
    protected void onResume() {
        super.onResume();

        login_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register_phone = new Intent(Login.this, LoginPhone.class);
                startActivity(to_register_phone);
                finish();
            }
        });

        login_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register_email = new Intent(Login.this, LoginEmail.class);
                startActivity(to_register_email);
                finish();
            }
        });

        login_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
