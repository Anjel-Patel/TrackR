package com.bits.trackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import static com.google.firebase.auth.PhoneAuthProvider.getInstance;

public class otp extends Activity {

    EditText field1, field2, field3, field4, field5, field6;
    private String otp, verificationId;
    private Button Verifyotp, resendotp;
    ConstraintLayout Layout_curr;
    Context Context_curr;
    private FirebaseAuth mAuth;

    TextView error_message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth = FirebaseAuth.getInstance();
        Layout_curr = findViewById(R.id.otp_layout);
        Context_curr = getBaseContext();

        field1 = findViewById(R.id.otp_phone_1);
        field2 = findViewById(R.id.otp_phone_2);
        field3 = findViewById(R.id.otp_phone_3);
        field4 = findViewById(R.id.otp_phone_4);
        field5 = findViewById(R.id.otp_phone_5);
        field6 = findViewById(R.id.otp_phone_6);
        Verifyotp = findViewById(R.id.verify_otp_button);


        //   resendotp = findViewById(R.id.rese)

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        String emailtxt = getIntent().getStringExtra("emailtxt");
        String usertxt = getIntent().getStringExtra("usertxt");

        sendVerificationCode(phoneNumber);
//        SharedPreferences prefs = getApplicationContext().getSharedPreferences("USER_PREF", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString("phoneNumber", phoneNumber);
//        editor.apply();
//        Toast otp_resent = Toast.makeText(getBaseContext(), "A new OTP has been sent", Toast.LENGTH_SHORT);

        EditText[] edit = {field1, field2, field3, field4, field5, field6};

        field1.addTextChangedListener(new OTPTextWatcher(field1, edit, Layout_curr, Context_curr));
        field2.addTextChangedListener(new OTPTextWatcher(field2, edit, Layout_curr, Context_curr));
        field3.addTextChangedListener(new OTPTextWatcher(field3, edit, Layout_curr, Context_curr));
        field4.addTextChangedListener(new OTPTextWatcher(field4, edit, Layout_curr, Context_curr));
        field5.addTextChangedListener(new OTPTextWatcher(field5, edit, Layout_curr, Context_curr));
        field6.addTextChangedListener(new OTPTextWatcher(field6, edit, Layout_curr, Context_curr));

        error_message = (TextView) findViewById(R.id.error_message_textview);
        Layout_curr.removeView(error_message);

        Verifyotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

        findViewById(R.id.resend_otp_button).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Code for resending the OTP
                Toast otp_resent = Toast.makeText(getBaseContext(), "A new OTP has been sent", Toast.LENGTH_SHORT);
                otp_resent.show();
            }
        });
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Intent intent = new Intent(otp.this, dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                              intent.putExtra("phoneNumber", phoneNumber);
//                              intent.putExtra("emailtxt", emailtxt);
//                              intent.putExtra("usertxt", usertxt);

                            startActivity(intent);

                        } else {
                            Toast.makeText(otp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

//        PhoneAuthOptions options =
//                PhoneAuthOptions.newBuilder(mAuth)
//                        .setPhoneNumber(phoneNumber)
//                        .setTimeout(60L, TimeUnit.SECONDS)
//                        .setActivity(this)
//                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                            @Override
//                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                                Toast.makeText(otp.this, "Enter OTP", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onVerificationFailed(@NonNull FirebaseException e) {
//                                Toast.makeText(otp.this, "Cannot create Account " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                Log.e("Error", e.getMessage());
//                            }
//
//                            @Override
//                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                                super.onCodeSent(s, forceResendingToken);
//                                Log.v("Success1", s);
//                                String codesent = s;
//                            }
//                        })          // OnVerificationStateChangedCallbacks
//                        .build();
//        PhoneAuthProvider.verifyPhoneNumber(options);
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
            Toast.makeText(otp.this, e.getMessage(), Toast.LENGTH_LONG).show();
            //WHERE OTP IS CHECKED AS INCORRECT(PROLLY AFTER CLICKING VERIFY OTP BUTTON)
            field1.setText("");
            field2.setText("");
            field3.setText("");
            field4.setText("");
            field5.setText("");
            field6.setText("");

            field1.setBackground(getDrawable(R.drawable.input_wrong));
            field2.setBackground(getDrawable(R.drawable.input_wrong));
            field3.setBackground(getDrawable(R.drawable.input_wrong));
            field4.setBackground(getDrawable(R.drawable.input_wrong));
            field5.setBackground(getDrawable(R.drawable.input_wrong));
            field6.setBackground(getDrawable(R.drawable.input_wrong));
            field1.requestFocus();

            if(Layout_curr.indexOfChild(error_message)==-1)
                Layout_curr.addView(error_message);
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent otp_to_Register = new Intent(otp.this, Register.class);
        startActivity(otp_to_Register);
        finish();
    }
}


//WHERE OTP IS RESENT
//Toast otp_resent = Toast.makeText(getBaseContext(), "A new OTP has been sent", Toast.LENGTH_SHORT);


