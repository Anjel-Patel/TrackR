package com.bits.trackr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bits.trackr.Model.TaskModel;
import com.bits.trackr.R;
import com.bits.trackr.TaskDetails;
import com.bits.trackr.dashboard;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;



public class TaskAdapter extends FirestoreRecyclerAdapter<TaskModel, TaskAdapter.TaskViewHolder> {
    private dashboard activity;

    public TaskAdapter(FirestoreRecyclerOptions<TaskModel> options){
        super(options);

    }

    @NotNull
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new TaskViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position, @NonNull TaskModel model){
            holder.task.setText(model.getTitle());
        String docId=getSnapshots().getSnapshot(position).getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), TaskDetails.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("content",model.getContent());
                intent.putExtra("taskId",docId);
                v.getContext().startActivity(intent);
            }

        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareTitle=model.getTitle();
                String shareContent=model.getContent();
                intent.putExtra(Intent.EXTRA_TEXT,"Task Title: "+shareTitle+"\nTask Description:"+shareContent);
                v.getContext().startActivity(intent);
                return false;
            }
        });
    }
    public Context getContext() {return activity; }

    private boolean toBoolean(int n){
        return n!=0;
    }
    public void deleteItem(int position){
        getSnapshots().getSnapshot(position).getReference().delete();
    }

//    public void setTasks(List<TaskModel> todoList){
//            this.todoList = todoList;
//            notifyDataSetChanged();
//    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        CheckBox task;
        public TaskViewHolder(@NonNull View itemView){
            super(itemView);
            task = itemView.findViewById(R.id.todo_checkbox);
        }
    }
}
