package com.bits.trackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

public class Register extends Activity {

    private EditText Username_field;
    public static boolean Username_valid;
    Context curr_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Username_field = (EditText) findViewById(R.id.UserName);
        curr_context = getBaseContext();
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        Username_field.addTextChangedListener(new UserNameWatcher(Username_field, curr_context));
        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true) //Replace with conditions for user authentication
                {
                    Intent Register_to_otp = new Intent(Register.this, otp.class);
                    startActivity(Register_to_otp);
                    finish();
                }
            }
        });
    }
}