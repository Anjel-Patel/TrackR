package com.bits.trackr;

import android.content.Context;
import android.graphics.BlendMode;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import java.util.regex.*;
import android.widget.EditText;

import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

public class UserNameWatcher implements TextWatcher {
    private EditText view_curr;
    GradientDrawable curr_input_wrong;
    Context curr_context;
    public UserNameWatcher(EditText view_curr, Context curr_context)
    {
        this.view_curr = view_curr;
        this.curr_context = curr_context;
        curr_input_wrong =(GradientDrawable)ContextCompat.getDrawable(curr_context, R.drawable.input_wrong);
    }
    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
//        boolean m = Pattern.matches("[\\d][\\s]",str);
        boolean m = Pattern.matches("[a-zA-Z0-9_]{4,15}",str);
        if(m)
        {
            view_curr.setBackgroundResource(R.drawable.input);
        }
        else
        {
            view_curr.setBackgroundResource(R.drawable.input_wrong);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

}
