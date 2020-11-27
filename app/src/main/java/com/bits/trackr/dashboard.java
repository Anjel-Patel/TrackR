package com.bits.trackr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bits.trackr.Adapter.TaskAdapter;
import com.bits.trackr.Model.TaskModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {

    private RecyclerView tasksRecyclerView;
    FirebaseFirestore fStore;
    //private TaskAdapter tasksAdapter;
    // private List<TaskModel> toDoModelList;
    FirebaseAuth fAuth;
    FragmentManager fragment_manager;
    FragmentTransaction fragment_transaction;
    TabLayout switcher_tab;
    FirebaseUser user;
    FloatingActionButton addTask;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference taskRef=db.collection("tasks");
    private TaskAdapter adapter;
    Button profile_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tasksRecyclerView = findViewById(R.id.tasksText);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        addTask=findViewById(R.id.addNewTask);
        profile_button = (Button)findViewById(R.id.profile_button);

//        switcher_tab = findViewById(R.id.tabLayout);
//        fragment_manager = getSupportFragmentManager();
//        fragment_transaction = fragment_manager.beginTransaction();
//        fragment_transaction.setReorderingAllowed(true)
//                .add(R.id.dashboard_fragment_container, Todo_Fragment.class, null)
//                .commit();
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dashboard.this, AddTask.class);
                v.getContext().startActivity(i);
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
}
