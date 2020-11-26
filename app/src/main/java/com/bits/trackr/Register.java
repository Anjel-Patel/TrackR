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

    private EditText Username, email, phone;
    private TextView Login;;
    private Button Register;

    public static boolean Username_valid;
    Context curr_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Username = findViewById(R.id.Email);
        Register = findViewById(R.id.registerButton);
        Login = findViewById(R.id.login_callout);
        phone = findViewById(R.id.Phone);
        email = findViewById(R.id.Password);

        curr_context = getBaseContext();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usertxt = Username.getText().toString().trim();
                String emailtxt = email.getText().toString().trim();
                String phonetxt = phone.getText().toString().trim();

                if (usertxt.isEmpty()) {
                    Username.setError("Enter Username");
                    Username.requestFocus();
                    return;
                }

                if (emailtxt.isEmpty()) {
                    email.setError("Enter Email");
                    email.requestFocus();
                    return;
                }

                if(!Patterns.EMAIL_ADDRESS.matcher(emailtxt).matches()) {
                    email.setError("Enter Valid Email Address");
                    email.requestFocus();
                    return;
                }

                if (phonetxt.isEmpty() || phonetxt.length() != 10) {
                    phone.setError("Enter Valid Number");
                    phone.requestFocus();
                    return;
                }

                String phoneNumber = "+91" + phonetxt;

                Intent intent = new Intent(Register.this, otp.class);
                intent.putExtra("phoneNumber", phoneNumber);
                intent.putExtra("emailtxt", emailtxt);
                intent.putExtra("usertxt", usertxt);

                startActivity(intent);

            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, EmailLoginActivity.class);
                startActivity(intent);
            }
        });

    }
}