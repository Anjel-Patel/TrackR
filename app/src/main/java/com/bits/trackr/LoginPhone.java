package com.bits.trackr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class LoginPhone extends AppCompatActivity {

    EditText phoneNo;// OTP;
    EditText field1, field2, field3, field4, field5, field6;
    private String otp, verificationId,phoneNumber, UserName, UserProfession, UId;
    private Button SendOTP, Verifyotp;
    ConstraintLayout Layout_curr;
    Context Context_curr;
    private FirebaseAuth mAuth;
    FirebaseFirestore fStore;
    String Full_phone_number;
    private FirebaseFirestore db;
    private DocumentReference reference;

    TextView error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);

//        Set from database
        UserName = "";
        UserProfession = "";

        phoneNo = findViewById(R.id.Phone);
//        OTP = findViewById(R.id.OTP);

        mAuth = FirebaseAuth.getInstance();
        Layout_curr = findViewById(R.id.phone_login_layout);
        Context_curr = getBaseContext();
        db = FirebaseFirestore.getInstance();


        field1 = findViewById(R.id.otp_phone_1);
        field2 = findViewById(R.id.otp_phone_2);
        field3 = findViewById(R.id.otp_phone_3);
        field4 = findViewById(R.id.otp_phone_4);
        field5 = findViewById(R.id.otp_phone_5);
        field6 = findViewById(R.id.otp_phone_6);
        SendOTP = findViewById(R.id.SendOTP);
        Verifyotp = findViewById(R.id.verify_otp_button);
        fStore = FirebaseFirestore.getInstance();

        EditText[] edit = {field1, field2, field3, field4, field5, field6};

        field1.addTextChangedListener(new OTPTextWatcher(field1, edit, Layout_curr, Context_curr));
        field2.addTextChangedListener(new OTPTextWatcher(field2, edit, Layout_curr, Context_curr));
        field3.addTextChangedListener(new OTPTextWatcher(field3, edit, Layout_curr, Context_curr));
        field4.addTextChangedListener(new OTPTextWatcher(field4, edit, Layout_curr, Context_curr));
        field5.addTextChangedListener(new OTPTextWatcher(field5, edit, Layout_curr, Context_curr));
        field6.addTextChangedListener(new OTPTextWatcher(field6, edit, Layout_curr, Context_curr));

        error_message = (TextView) findViewById(R.id.error_message_textview);
        Layout_curr.removeView(error_message);

        SendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = phoneNo.getText().toString().trim();

                if (phoneNumber.isEmpty() || !only10Digits(phoneNumber)) {
                    phoneNo.setError("Enter Valid Number");
                    phoneNo.requestFocus();
                    return;
                }

                String PhoneNumber = "+91"+phoneNumber;
                Full_phone_number = PhoneNumber;
                sendVerificationCode(PhoneNumber);
            }
        });

        Verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                otp = OTP.getText().toString().trim();

                otp =    field1.getText().toString()
                        +field2.getText().toString()
                        +field3.getText().toString()
                        +field4.getText().toString()
                        +field5.getText().toString()
                        +field6.getText().toString();

                if (otp.isEmpty() || otp.length() < 6) {
                    //field3.setError("Enter code...");
                    field1.requestFocus(); field2.requestFocus();
                    field3.requestFocus(); field4.requestFocus();
                    field5.requestFocus(); field6.requestFocus();
                    return;
                }

                verifyCode(otp);
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
        String str;
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            UId = mAuth.getUid();
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
                                                 prefs.edit().putString("PhoneNumber", Full_phone_number).commit();
                                                 prefs.edit().putString("UserName", UserName).commit();
                                                 prefs.edit().putString("UserProfession", UserProfession).commit();
                                             }
                                             else {
                                                 Toast.makeText(getBaseContext(), "Document does not exist", Toast.LENGTH_SHORT).show();
                                             }
                                         }
                                     });

                            Intent intent = new Intent(LoginPhone.this, dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginPhone.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            UI_verification_failed();
                        }
                    }
                });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallBack)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
//               otp.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(Context_curr, "Bad Read", Toast.LENGTH_SHORT).show();
            Toast.makeText(LoginPhone.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent otp_to_Register = new Intent(LoginPhone.this, Login.class);
        startActivity(otp_to_Register);
        finish();
    }

    private boolean only10Digits(String phonetxt) {
        return Pattern.compile("[0-9]{10}").matcher(phonetxt).matches();
    }

    private void UI_verification_failed()
    {
        field1.setText("");
        field2.setText("");
        field3.setText("");
        field4.setText("");
        field5.setText("");
        field6.setText("");

        field1.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.input_wrong));
        field2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.input_wrong));
        field3.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.input_wrong));
        field4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.input_wrong));
        field5.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.input_wrong));
        field6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.input_wrong));
        field1.requestFocus();

        if(Layout_curr.indexOfChild(error_message)==-1)
            Layout_curr.addView(error_message);
    }
}