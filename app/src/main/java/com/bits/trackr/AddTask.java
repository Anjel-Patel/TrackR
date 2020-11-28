package com.bits.trackr;

import android.content.Intent;
import android.os.Bundle;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddTask extends AppCompatActivity {
    FirebaseFirestore fstore;
    EditText taskContent, taskTitle;
    FirebaseUser user;
    Intent data;
    Button newTask;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_task);
        data = getIntent();
        fstore = FirebaseFirestore.getInstance();
        taskContent = findViewById(R.id.newTaskContent);
        taskTitle = findViewById(R.id.newTaskTitle);
        user = FirebaseAuth.getInstance().getCurrentUser();
        newTask = findViewById(R.id.addedNewTaskButton);
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = taskTitle.getText().toString();
                String Content = taskContent.getText().toString();

                if(Title.isEmpty()||Content.isEmpty()){
                    Toast.makeText(AddTask.this, "Cannot save tasks with Empty Fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                DocumentReference docref = fstore.collection("tasks").document(user.getUid())
                        .collection("myTasks").document();
                Map<String,Object> task = new HashMap<>();
                task.put("title",Title);
                task.put("content",Content);
                task.put("taskId",docref.getId());
                docref.set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddTask.this, "Task Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddTask.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent to_dashboard = new Intent(AddTask.this, dashboard.class);
        startActivity(to_dashboard);
        finish();
    }
}
