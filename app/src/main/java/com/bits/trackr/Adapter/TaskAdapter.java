package com.bits.trackr.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bits.trackr.Model.TaskModel;
import com.bits.trackr.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;

//import java.util.List;

public class TaskAdapter extends FirestoreRecyclerAdapter<TaskModel, TaskAdapter.TaskViewHolder> {
 //   private List<TaskModel> todoList;

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
    }

    private boolean toBoolean(int n){
        return n!=0;
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
