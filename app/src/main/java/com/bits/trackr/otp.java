package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

public class otp extends Activity {
    EditText field1;
    EditText field2;
    EditText field3;
    EditText field4;
    ConstraintLayout Layout_curr;
    TextView error_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Layout_curr = (ConstraintLayout)findViewById(R.id.otp_layout);
        field1 = (EditText)findViewById(R.id.otp_phone_1);
        field2 = (EditText)findViewById(R.id.otp_phone_2);
        field3 = (EditText)findViewById(R.id.otp_phone_3);
        field4 = (EditText)findViewById(R.id.otp_phone_4);

        EditText[] edit = {field1, field2, field3, field4};

        field1.addTextChangedListener(new OTPTextWatcher(field1, edit));
        field2.addTextChangedListener(new OTPTextWatcher(field2, edit));
        field3.addTextChangedListener(new OTPTextWatcher(field3, edit));
        field4.addTextChangedListener(new OTPTextWatcher(field4, edit));

        error_message = (TextView) findViewById(R.id.error_message_textview);
        Layout_curr.removeView(error_message);
    }

    @Override
    protected void onResume() {
        super.onResume();

        findViewById(R.id.verify_otp_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(false) //Condition to verify OTP
                {
                    Intent otp_to_emailverification = new Intent(otp.this, emailVerification.class);
                    startActivity(otp_to_emailverification);
                    finish();
                }
                else
                {
                    field1.setBackground(getDrawable(R.drawable.input_wrong));
                    field2.setBackground(getDrawable(R.drawable.input_wrong));
                    field3.setBackground(getDrawable(R.drawable.input_wrong));
                    field4.setBackground(getDrawable(R.drawable.input_wrong));

                    Layout_curr.addView(error_message);
                }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent otp_to_Register = new Intent(otp.this, Register.class);
        startActivity(otp_to_Register);
        finish();
    }
}

//public class TextWatch
//{
//    public
//}