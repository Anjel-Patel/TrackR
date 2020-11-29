package com.bits.trackr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class emailVerification extends AppCompatActivity {

    private static final String TAG = "Register";
    //    Button Register;
    String passwordtxt;
    private FirebaseAuth fAuth;
    private TextView logintxt;
    String emailtxt, usertxt, UserProfession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

//        set from database
        UserProfession = "";

        fAuth = FirebaseAuth.getInstance();;
//        Register = findViewById(R.id.button);
        logintxt = findViewById(R.id.logintxt);
        passwordtxt = getIntent().getStringExtra("passtxt");
        emailtxt = getIntent().getStringExtra("emailtxt");
        usertxt = getIntent().getStringExtra("usertxt");

        fAuth.createUserWithEmailAndPassword(emailtxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(usertxt).build();
                    user.updateProfile(request);

//                    Toast.makeText(emailVerification.this, "User Registered successfully.", Toast.LENGTH_SHORT).show();
                    try {
                        if (user != null)
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(emailVerification.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                                        logintxt.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                loginUser(emailtxt, passwordtxt);
                                            }
                                        });
                                    }
                                }
                            });

                    } catch (Exception e) {
                        Log.d("second last exception",e.getMessage());//2nd last exception
                    }
                }
                else {
                    if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                        Toast.makeText(emailVerification.this, " This Email is already registered.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(emailVerification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void loginUser(String txt_email, String txt_password) {
        fAuth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(emailVerification.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = fAuth.getCurrentUser();

                if(task.isSuccessful()) {
                    if(user.isEmailVerified()) {
                        Toast.makeText(emailVerification.this, "Login Successful", Toast.LENGTH_SHORT).show();

                        SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);
                        prefs.edit().putString("login_state", "1").commit();
                        prefs.edit().putString("UserName", usertxt).commit();
                        prefs.edit().putString("UserEmail", txt_email).commit();
                        prefs.edit().putString("UserProfession", UserProfession).commit();

                        Intent intent = new Intent(emailVerification.this, dashboard.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                    else {
                        //email.setError("Email is not verified. Please verify.");
                        Toast.makeText(emailVerification.this, "Email is not verified. Please verify.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
//                    email.setError("This Email is not registered");
//                    password.setError("Email and password combinaton does not match");
//                    email.requestFocus();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent emailverification_to_otp = new Intent(emailVerification.this, Register.class);
        startActivity(emailverification_to_otp);
        finish();
    }
}
