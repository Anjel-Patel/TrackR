package com.bits.trackr;

import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.*;
import android.app.Activity;
import android.widget.EditText;
import java.util.regex.*;

public class UserNameWatcher implements TextWatcher {
    private EditText view_curr;
    public UserNameWatcher(EditText view_curr)
    {
        this.view_curr = view_curr;
    }
    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        Pattern p = Pattern.compile("[\\s]");
        boolean m = Pattern.matches("[\\s]",str);
        if(m)
        {
//            view_curr.setBackground();
        }
        else
        {
//            view_curr.setBackground();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

}
