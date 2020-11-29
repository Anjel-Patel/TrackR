package com.bits.trackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginEmail extends Activity {
    private EditText email, password;
    private Button login;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private DocumentReference reference;
    //Set this from database.
    private String UserName, UserProfession, UId;
    private FirebaseUser Curr_User;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        db = FirebaseFirestore.getInstance();

//        Set from database
        UserName = "";
        UserProfession = "";

        email = findViewById(R.id.Email);
        password = findViewById(R.id.Password);
        login = findViewById(R.id.Login);
        auth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_email, txt_password);
            }
        });
    }

    private void loginUser(String txt_email, String txt_password) {
        auth.signInWithEmailAndPassword(txt_email, txt_password).addOnCompleteListener(LoginEmail.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Curr_User = auth.getCurrentUser();
                    UId = Curr_User.getUid();
                    reference = db.collection("users").document(UId);
                    reference.get()
                             .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()) {
                                        UserName = documentSnapshot.getString("username");
                                        UserProfession = documentSnapshot.getString("profession");

                                        SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);
                                        prefs.edit().putString("login_state", "1").commit();
                                        prefs.edit().putString("UserName", UserName).commit();
                                        prefs.edit().putString("UserEmail", txt_email).commit();
                                        prefs.edit().putString("UserProfession", UserProfession).commit();
                                    }
                                    else {
                                        Toast.makeText(getBaseContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });

                    //FOR LOGIN BYPASS

                    Toast.makeText(LoginEmail.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginEmail.this, dashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else {
                    email.setError("");
                    password.setError("Email and password combinaton does not match");
                    password.requestFocus();
                }
            }
        });
    }
}
