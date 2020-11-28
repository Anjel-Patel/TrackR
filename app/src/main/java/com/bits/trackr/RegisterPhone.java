package com.bits.trackr;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

public class RegisterPhone extends Activity {
    private EditText field1, field2, field3, field4, field5, field6;
    private Button Verifyotp;
    private EditText Username, phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_register);

        Username = findViewById(R.id.UserName);
        phone = findViewById(R.id.Phone);

        field1 = findViewById(R.id.otp_phone_1);
        field2 = findViewById(R.id.otp_phone_2);
        field3 = findViewById(R.id.otp_phone_3);
        field4 = findViewById(R.id.otp_phone_4);
        field5 = findViewById(R.id.otp_phone_5);
        field6 = findViewById(R.id.otp_phone_6);
        Verifyotp = findViewById(R.id.verify_otp_button);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
