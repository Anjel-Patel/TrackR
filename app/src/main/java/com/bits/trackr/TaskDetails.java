package com.bits.trackr;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class TaskDetails extends AppCompatActivity {
    Intent data;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_details);
        data=getIntent();
        TextView content=findViewById(R.id.TaskContent);
        TextView title=findViewById(R.id.TaskTitle);
        content.setMovementMethod(new ScrollingMovementMethod());
        content.setText(data.getStringExtra("content"));
        title.setText(data.getStringExtra("title"));
        Button editTask=findViewById(R.id.EditTaskButton);
        editTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(v.getContext(),EditTask.class);
                i.putExtra("title",data.getStringExtra("title"));
                i.putExtra("content",data.getStringExtra("content"));
                i.putExtra("taskId",data.getStringExtra("taskId"));
                startActivity(i);
                finish();
            }
        });
    }
}