package com.bits.trackr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.auth.FirebaseAuth;

public class ProfilePage extends Activity {
    Button logout_button;
    Button profile_back_button;
    ConstraintLayout Layout_curr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String UserName, UserEmail, PhoneNumber, UserProfession;
        TextView UserName_text, UserEmail_text, PhoneNumber_text, UserProfession_text;
        ConstraintLayout UserName_layout, UserEmail_layout, PhoneNumber_layout, UserProfession_layout;

        SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);
        UserName = prefs.getString("UserName", "");
        UserEmail = prefs.getString("UserEmail", "");
        PhoneNumber = prefs.getString("Profession", "");
        UserProfession = prefs.getString("UserProfession", "");

        UserName_text = findViewById(R.id.UserName);
        UserEmail_text = findViewById(R.id.UserEmail);
        PhoneNumber_text = findViewById(R.id.PhoneNumber);
        UserProfession_text = findViewById(R.id.UserProfession);

        Layout_curr =  findViewById(R.id.profile_layout);
        UserName_layout = Layout_curr.findViewById(R.id.username_display);
        UserEmail_layout = Layout_curr.findViewById(R.id.useremail_display);
        PhoneNumber_layout = Layout_curr.findViewById(R.id.phonenumber_display);
        UserProfession_layout = Layout_curr.findViewById(R.id.userprofession_display);

        if (UserName == "")
            ((LinearLayout)UserName_layout.getParent()).removeView(findViewById(R.id.username_display));
        if (UserEmail == "")
            ((LinearLayout)UserEmail_layout.getParent()).removeView(findViewById(R.id.useremail_display));
        if (PhoneNumber == "")
            ((LinearLayout)PhoneNumber_layout.getParent()).removeView(findViewById(R.id.phonenumber_display));
        if (UserProfession == "")
            ((LinearLayout)UserProfession_layout.getParent()).removeView(findViewById(R.id.userprofession_display));


        UserName_text.setText(UserName);
        UserEmail_text.setText(UserEmail);
        PhoneNumber_text.setText(PhoneNumber);
        UserProfession_text.setText(UserProfession);

        logout_button = (Button) findViewById(R.id.logout_button);
        profile_back_button = (Button) findViewById(R.id.profile_back_button);
    }

    @Override
    protected void onResume() {
        super.onResume();
            logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);
                prefs.edit().putString("login_state", "0").commit();
                prefs.edit().putString("UserName", "").commit();
                prefs.edit().putString("UserEmail", "").commit();
                prefs.edit().putString("PhoneNumber", "").commit();

                Toast.makeText(ProfilePage.this,"Signed out succesfully",Toast.LENGTH_SHORT);
                Intent back_to_registration=new Intent(ProfilePage.this,Register.class);
                startActivity(back_to_registration);
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
//
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent profilepage_to_dashboard = new Intent(ProfilePage.this, dashboard.class);
        startActivity(profilepage_to_dashboard);
        finish();
    }
}
