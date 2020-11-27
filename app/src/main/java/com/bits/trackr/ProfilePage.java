package com.bits.trackr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;

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
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfilePage.this,"Signed out succesfully",Toast.LENGTH_SHORT);
                Intent i=new Intent(ProfilePage.this,Register.class);
                startActivity(i);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent profilepage_to_dashboard = new Intent(ProfilePage.this, dashboard.class);
        startActivity(profilepage_to_dashboard);
        finish();
    }
}
