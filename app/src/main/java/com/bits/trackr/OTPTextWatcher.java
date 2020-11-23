package com.bits.trackr;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

public class OTPTextWatcher implements TextWatcher {
    private final EditText[] editText;
    private View view;
    public OTPTextWatcher(View view, EditText editText[])
    {
        this.editText = editText;
        this.view = view;
    }

    @Override
    public void afterTextChanged(Editable editable) {
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