package com.bits.trackr;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bits.trackr.dashboard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EditTask extends AppCompatActivity {
    Intent data;
    EditText editTaskTitle,editTaskContent;
    FirebaseFirestore fStore;
    FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task);
        data = getIntent();
        fStore=FirebaseFirestore.getInstance();
        editTaskTitle = findViewById(R.id.EditTaskTitle);
        editTaskContent = findViewById(R.id.EditTaskContent);
        user = FirebaseAuth.getInstance().getCurrentUser();
        String taskTitle= data.getStringExtra("title");
        String taskContent= data.getStringExtra("content");
        editTaskTitle.setText(data.getStringExtra("title"));
        editTaskContent.setText(data.getStringExtra("content"));
        Button saveEditedTask=findViewById(R.id.saveEditedTaskButton);
        saveEditedTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Title=editTaskTitle.getText().toString();
                String Content=editTaskContent.getText().toString();
                if(Title.isEmpty()||Content.isEmpty()){
                    Toast.makeText(EditTask.this,"Cannot Save Task with Empty fields",Toast.LENGTH_SHORT);
                    return;
                }
                DocumentReference docref=fStore.collection("tasks").
                        document(user.getUid()).
                        collection("myTasks").
                        document(data.getStringExtra("taskId"));

                Map<String,Object> task = new HashMap<>();
                task.put("title",Title);
                task.put("content",Content);

                docref.update(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditTask.this,"Task Updated",Toast.LENGTH_SHORT);
                        startActivity(new Intent(getApplicationContext(),dashboard.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditTask.this,"Task not updated, try again",Toast.LENGTH_SHORT);

                    }
                });
            }
        });
    }
}
