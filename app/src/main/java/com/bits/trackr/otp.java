package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

public class otp extends Activity {
    View field1;
    View field2;
    View field3;
    View field4;
    ConstraintLayout Layout_curr;
    TextView error_message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        Layout_curr = (ConstraintLayout)findViewById(R.id.otp_layout);
        field1 = (View)findViewById(R.id.otp_phone_1);
        field2 = (View)findViewById(R.id.otp_phone_2);
        field3 = (View)findViewById(R.id.otp_phone_3);
        field4 = (View)findViewById(R.id.otp_phone_4);
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