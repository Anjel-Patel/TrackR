package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_verification);

        fAuth = FirebaseAuth.getInstance();;
//        Register = findViewById(R.id.button);

        String passwordtxt = getIntent().getStringExtra("passtxt");
        String emailtxt = getIntent().getStringExtra("emailtxt");
        String usertxt = getIntent().getStringExtra("usertxt");

        fAuth.createUserWithEmailAndPassword(emailtxt, passwordtxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder().setDisplayName(usertxt).build();
                    user.updateProfile(request);

                    Toast.makeText(emailVerification.this, "User Registered successfully.", Toast.LENGTH_SHORT).show();
                    try {
                        if (user != null)
                            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(emailVerification.this, "Verification Email sent", Toast.LENGTH_SHORT).show();
                                                //Log.d(TAG, "Email sent.");
                                                Toast.makeText(emailVerification.this, "Email sent", Toast.LENGTH_SHORT).show();
                                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(emailVerification.this);
                                                alertDialogBuilder.setTitle("Please Verify Your EmailID");

                                                alertDialogBuilder
                                                        .setMessage("A verification Email Is Sent To Your Registered EmailID, please click on the link and Sign in again!")
                                                        .setCancelable(false)
                                                        .setPositiveButton("Sign In", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                emailVerification.this.finish();
                                                            }
                                                        });
                                                AlertDialog alertDialog = alertDialogBuilder.create();
                                                alertDialog.show();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent emailverification_to_registerEmail = new Intent(emailVerification.this, RegisterEmail.class);
        startActivity(emailverification_to_registerEmail);
        finish();
    }

}