package com.bits.trackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

        signup_phone = (Button)findViewById(R.id.SignupPhone);
        signup_email = (Button)findViewById(R.id.SignupEmail);
        signup_google = (Button)findViewById(R.id.SignupGoogle);
        login_callout = (TextView) findViewById(R.id.login_callout);

//        Username = findViewById(R.id.Email);
//        Register = findViewById(R.id.registerButton);
//        Login = findViewById(R.id.login_callout);
//        phone = findViewById(R.id.Phone);
//        email = findViewById(R.id.Password);
//
//        curr_context = getBaseContext();
//
//        Register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                String usertxt = Username.getText().toString().trim();
//                String emailtxt = email.getText().toString().trim();
//                String phonetxt = phone.getText().toString().trim();
//
//                if (usertxt.isEmpty()) {
//                    Username.setError("Enter Username");
//                    Username.requestFocus();
//                    return;
//                }
//
//                if (emailtxt.isEmpty()) {
//                    email.setError("Enter Email");
//                    email.requestFocus();
//                    return;
//                }
//
//                if(!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()) {
//                    email.setError("Enter Valid Email Address");
//                    email.requestFocus();
//                    return;
//                }
//
//                if (phonetxt.isEmpty() || phonetxt.length() != 10) {
//                    phone.setError("Enter Valid Number");
//                    phone.requestFocus();
//                    return;
//                }
//
//                String phoneNumber = "+91" + phonetxt;
//
//                Intent intent = new Intent(Register.this, otp.class);
//                intent.putExtra("phoneNumber", phoneNumber);
//                intent.putExtra("emailtxt", emailtxt);
//                intent.putExtra("usertxt", usertxt);
//
//                startActivity(intent);
//
//            }
//        });
//
//        Login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Register.this, EmailLoginActivity.class);
//                startActivity(intent);
//            }
//        });
//
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
}