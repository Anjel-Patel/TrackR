package com.bits.trackr;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

public class OTPTextWatcher implements TextWatcher {
    private final EditText[] editText;
    private View view;
    ConstraintLayout Layout_curr;
    TextView error_message_text;
    Context Context_curr;
    public OTPTextWatcher(View view, EditText editText[], ConstraintLayout Layout_curr, Context Context_curr)
    {
        this.editText = editText;
        this.view = view;
        this.Layout_curr = Layout_curr;
        this.Context_curr = Context_curr;
        error_message_text = Layout_curr.findViewById(R.id.error_message_textview);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(Layout_curr.indexOfChild(error_message_text)!=-1)
            Layout_curr.removeView(error_message_text);
        editText[0].setBackground(ContextCompat.getDrawable(Context_curr,R.drawable.input));
        editText[1].setBackground(ContextCompat.getDrawable(Context_curr,R.drawable.input));
        editText[2].setBackground(ContextCompat.getDrawable(Context_curr,R.drawable.input));
        editText[3].setBackground(ContextCompat.getDrawable(Context_curr,R.drawable.input));


        String text = editable.toString();
        switch (view.getId()) {
            case R.id.otp_phone_1:
                if (text.length() == 1)
                    editText[1].requestFocus();
                break;
            case R.id.otp_phone_2:
                if (text.length() == 1)
                    editText[2].requestFocus();
                else if (text.length() == 0)
                    editText[0].requestFocus();
                break;
            case R.id.otp_phone_3:
                if (text.length() == 1)
                    editText[3].requestFocus();
                else if (text.length() == 0)
                    editText[1].requestFocus();
                break;
            case R.id.otp_phone_4:
                if (text.length() == 0)
                    editText[2].requestFocus();
                break;
        }
    }
    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
    }

    @Override
    public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

    }
}