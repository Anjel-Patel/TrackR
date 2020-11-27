package com.bits.trackr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bits.trackr.Model.CategoryModel;
import com.bits.trackr.R;

import com.bits.trackr.dashboard;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;



public class CategoryAdapter extends FirestoreRecyclerAdapter<CategoryModel, CategoryAdapter.CategoryViewHolder> {
    private dashboard activity;

    public CategoryAdapter(FirestoreRecyclerOptions<CategoryModel> options){
        super(options);

    }

    @NotNull
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cat_card_layout, parent, false);
        return new CategoryViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position, @NonNull CategoryModel model){

        holder.category.setText(model.getTitle());
        String docId=getSnapshots().getSnapshot(position).getId();
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(v.getContext(), CategoryContents.class);
//                intent.putExtra("title",model.getTitle());
//                intent.putExtra("taskId",docId);
//                v.getContext().startActivity(intent);
//            }
//        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareTitle=model.getTitle();
                intent.putExtra(Intent.EXTRA_TEXT,"Category Title: "+shareTitle);
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

    public class CategoryViewHolder extends RecyclerView.ViewHolder{
        Button category;
        public CategoryViewHolder(@NonNull View itemView){
            super(itemView);
            category= itemView.findViewById(R.id.cat_card);
        }
    }
}
