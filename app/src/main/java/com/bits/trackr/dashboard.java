package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bits.trackr.Adapter.TaskAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;
    FirebaseFirestore fStore;
    //private TaskAdapter tasksAdapter;
    // private List<TaskModel> toDoModelList;
    FirebaseAuth fAuth;
    TabLayout switcher_tab;
    FirebaseUser user;
    FloatingActionButton addTask;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference taskRef=db.collection("tasks");
    private TaskAdapter adapter;
    Button profile_button;

    Fragment todo_fragment;
    Fragment cat_fragment;
    private String tab_position;
    private String login_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tasksRecyclerView = findViewById(R.id.tasksText);
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("TrackR", Context.MODE_PRIVATE);

        setContentView(R.layout.activity_dashboard);
        addTask=findViewById(R.id.addNewTask);
        profile_button = (Button)findViewById(R.id.profile_button);

        todo_fragment = new Todo_Fragment();
        cat_fragment = new Cat_Fragment();
        switcher_tab = findViewById(R.id.tabLayout);

        tab_position = prefs.getString("dashboard_tab_state", "0");
        TabLayout.Tab tab = switcher_tab.getTabAt(Integer.valueOf(tab_position));
        switcher_tab.selectTab(tab);

        switch (tab_position) {
            case "0":
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.dashboard_fragment_container, todo_fragment, null)
                        .commit();
                break;
            case "1":
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .add(R.id.dashboard_fragment_container, cat_fragment, null)
                        .commit();
                break;
        }
        //THAT LOGIN BYPASS THING
        this.login_state = prefs.getString("login_state", "0");
//        Toast.makeText(getBaseContext(), login_state, Toast.LENGTH_SHORT).show();

        switcher_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                tab_position = String.valueOf(tab.getPosition());
                prefs.edit().putString("dashboard_tab_state", String.valueOf(tab_position)).commit();

                switch (tab_position)
                {
                    case "0":
                    {
//                        Toast.makeText(getBaseContext(), "TODO clicked", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.dashboard_fragment_container, todo_fragment, null)
                                .commit();
                        break;
                    }
                    case "1":
                    {
//                        Toast.makeText(getBaseContext(), "Categories clicked", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager().beginTransaction()
                                .setReorderingAllowed(true)
                                .replace(R.id.dashboard_fragment_container, cat_fragment, null)
                                .commit();
                        break;
                    }
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //this is for ToDo
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(switcher_tab.getSelectedTabPosition())
                {
                    case 0: {
                        Intent i = new Intent(dashboard.this, AddTask.class);
                        v.getContext().startActivity(i);
                        finish();
                        break;
                    }
                    case 1:
                    {
                        Intent i = new Intent(dashboard.this, AddCategory.class);
                        v.getContext().startActivity(i);
                        finish();
                        break;
                    }
                }
            }
        });
        profile_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dashboard_to_profilepage = new Intent(dashboard.this, ProfilePage.class);
                startActivity(dashboard_to_profilepage);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
