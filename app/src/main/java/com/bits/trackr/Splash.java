package com.bits.trackr;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class SplashActivity extends AppCompatActivity {
    private static int SplashTime = 1000;

    @Override
    protected void onCreate(Bundle SavedInstanceState)
    {
        super.onCreate(Bundle SavedInstanceState);
        setContentView(R.id.activity_splash);
    }

}
