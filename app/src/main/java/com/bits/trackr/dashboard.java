package com.bits.trackr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bits.trackr.Adapter.TaskAdapter;
import com.bits.trackr.Model.TaskModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;

public class dashboard extends AppCompatActivity {

   // private RecyclerView tasksRecyclerView;
    //private TaskAdapter tasksAdapter;
    // private List<TaskModel> toDoModelList;
    FirebaseAuth fauth;
    FirebaseUser user;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference taskRef=db.collection("tasks");

    private TaskAdapter adapter;
    //Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpRecyclerView();


    }
    private void setUpRecyclerView()
    {
        Query query=taskRef.orderBy("title",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<TaskModel> options= new FirestoreRecyclerOptions.Builder<TaskModel>()
                .setQuery(query, TaskModel.class)
                .build();
        adapter=new TaskAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.tasksText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
