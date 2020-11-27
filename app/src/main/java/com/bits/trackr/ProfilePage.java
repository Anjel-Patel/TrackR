package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

public class ProfilePage extends Activity {
    Button logout_button;
    Button profile_back_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logout_button = (Button)findViewById(R.id.logout_button);
        profile_back_button = (Button)findViewById(R.id.profile_back_button);

        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Something that happens on logout.
                Intent profilepage_to_register = new Intent(ProfilePage.this, Register.class);
                startActivity(profilepage_to_register);
                finish();
            }
        });

        profile_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profilepage_to_dashboard = new Intent(ProfilePage.this, dashboard.class);
                startActivity(profilepage_to_dashboard);
                finish();
            }
        });
    }
}
