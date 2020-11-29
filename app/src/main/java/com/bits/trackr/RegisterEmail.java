package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.util.regex.Pattern;

public class RegisterEmail extends Activity {
    private EditText Username, email, password, profession;
    private Button Register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_register);

        Username = findViewById(R.id.UserName);
        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        Register = findViewById(R.id.registerButton);
        profession = findViewById(R.id.Profession);
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
                String proftxt = profession.getText().toString().trim().toLowerCase();

                if (usertxt.isEmpty()) {
                    Username.setError("Enter Username");
                    Username.requestFocus();
                    return;
                }

                if(!isalpha(usertxt)) {
                    Username.setError("Enter valid User");
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
                    password.setError("Enter Password");
                    password.requestFocus();
                    return;
                }

                if(!passwordregex(passtxt)) {
                    password.setError("Enter Strong Password");
                    password.requestFocus();
                    return;
                }

                if (proftxt.isEmpty()) {
                    profession.setError("Enter Profession");
                    profession.requestFocus();
                    return;
                }

                else if(!isalpha(proftxt)) {
                    profession.setError("Enter Valid Profession");
                    profession.requestFocus();
                    return;
                }
                else
                    proftxt = proftxt.substring(0, 1).toUpperCase()+proftxt.substring(1);

                Intent intent = new Intent(RegisterEmail.this, emailVerification.class);
                intent.putExtra("passtxt", passtxt);
                intent.putExtra("emailtxt", emailtxt);
                intent.putExtra("usertxt", usertxt);
                intent.putExtra("proftxt", proftxt);

                startActivity(intent);

            }
        });
    }

    private boolean passwordregex(String passtxt) {
        return Pattern.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$", passtxt);
    }

    private boolean isalpha(String usertxt) {
        return Pattern.matches("[A-Za-z_ ]{2,15}", usertxt);
    }
}
