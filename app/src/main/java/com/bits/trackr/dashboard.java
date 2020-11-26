package com.bits.trackr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bits.trackr.Adapter.TaskAdapter;
import com.bits.trackr.Model.TaskModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
    FirebaseUser user;
    FloatingActionButton addTask;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference taskRef=db.collection("tasks");
    private TaskAdapter adapter;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        tasksRecyclerView = findViewById(R.id.tasksText);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpRecyclerView();
        addTask=findViewById(R.id.addNewTask);
        data=getIntent();
        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(dashboard.this, AddTask.class);
                v.getContext().startActivity(i);
            }
        });
    }
    private void setUpRecyclerView()
    {
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        Query query = fStore.collection("tasks")
                .document(user.getUid())
                .collection("myTasks")
                .orderBy("title",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<TaskModel> options= new FirestoreRecyclerOptions.Builder<TaskModel>()
                .setQuery(query, TaskModel.class)
                .build();
        adapter=new TaskAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.tasksText);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            if(direction==ItemTouchHelper.LEFT)
                {
                    viewHolder.itemView.setBackgroundColor(R.color.error_red);
                    adapter.deleteItem(viewHolder.getAdapterPosition());
                }
            }
            public void onItemClicked(@NonNull RecyclerView.ViewHolder viewHolder){
                Toast.makeText(dashboard.this, "Item clicked at " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
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
