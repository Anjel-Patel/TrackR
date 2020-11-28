package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class RegisterEmail extends Activity {
    private EditText Username, email, password;
    private Button Register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        Username = findViewById(R.id.UserName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        Register = findViewById(R.id.registerButton);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usertxt = Username.getText().toString().trim();
                String emailtxt = email.getText().toString().trim();
                String passtxt = password.getText().toString().trim();

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

                if (passtxt.isEmpty()) {
                    password.setError("Enter Valid Number");
                    password.requestFocus();
                    return;
                }

                Intent intent = new Intent(RegisterEmail.this, emailVerification.class);
                intent.putExtra("passtxt", passtxt);
                intent.putExtra("emailtxt", emailtxt);
                intent.putExtra("usertxt", usertxt);

                startActivity(intent);

            }
        });
    }
}
