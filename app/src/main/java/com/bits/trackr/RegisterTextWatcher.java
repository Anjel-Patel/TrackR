package com.bits.trackr;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import java.util.regex.*;

public class RegisterTextWatcher implements TextWatcher{
    private EditText[] input_fields;
    private View view_curr;
    public RegisterTextWatcher(EditText input_fields[], View view_curr)
    {
        this.input_fields = input_fields;
        this.view_curr = view_curr;
    }

    @Override
    public void afterTextChanged(Editable s) {
        switch(view_curr.getId())
        {
            case R.id.PersonName:
            {

            }
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
}
