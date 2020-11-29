package com.bits.trackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Register extends Activity {

//    private EditText Username, email, phone;
//    private TextView Login;;
//    private Button Register;
    private Button signup_phone;
    private Button signup_email;
    private Button signup_google;
    private TextView login_callout;

    public static boolean Username_valid;
    Context curr_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_first);

        SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);
        prefs.edit().putString("login_state", "0").commit();
        prefs.edit().putString("UserName", "").commit();
        prefs.edit().putString("UserEmail", "").commit();
        prefs.edit().putString("PhoneNumber", "").commit();

        signup_phone = (Button)findViewById(R.id.SignupPhone);
        signup_email = (Button)findViewById(R.id.SignupEmail);
        signup_google = (Button)findViewById(R.id.SignupGoogle);
        login_callout = (TextView) findViewById(R.id.login_callout);
    }

    @Override
    protected void onResume() {
        super.onResume();

        signup_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register_phone = new Intent(Register.this, RegisterPhone.class);
                startActivity(to_register_phone);
                finish();
            }
        });

        signup_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_register_email = new Intent(Register.this, RegisterEmail.class);
                startActivity(to_register_email);
                finish();
            }
        });

        signup_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        login_callout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_login_page = new Intent(Register.this, Login.class);
                startActivity(to_login_page);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}