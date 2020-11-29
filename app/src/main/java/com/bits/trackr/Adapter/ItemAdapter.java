package com.bits.trackr.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bits.trackr.CategoryContent;

import com.bits.trackr.EditItem;
import com.bits.trackr.Model.ItemModel;

import com.bits.trackr.R;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import org.jetbrains.annotations.NotNull;



public class ItemAdapter extends FirestoreRecyclerAdapter<ItemModel, ItemAdapter.ItemViewHolder> {
    private CategoryContent activity;
    String categoryid;
    public ItemAdapter(FirestoreRecyclerOptions<ItemModel> options){
        super(options);


    }
    public void setCategoryId(String id){
        categoryid=id;
    }
    @NotNull
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_layout, parent, false);
        return new ItemViewHolder(itemView);
    }

    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ItemModel model){

        holder.title.setText(model.getTitle());
        holder.quantity.setText(model.getQuantity());
        String itemId=getSnapshots().getSnapshot(position).getId();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), EditItem.class);
                intent.putExtra("title",model.getTitle());
                intent.putExtra("quantity",model.getQuantity());
                intent.putExtra("itemid",itemId);
                intent.putExtra("categoryid",categoryid);
                v.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String shareTitle=model.getTitle();
                String shareQuantity=model.getQuantity();
                intent.putExtra(Intent.EXTRA_TEXT,"Item Title: "+shareTitle+"\nItem Quantity:"+shareQuantity);
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

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView quantity;
        public ItemViewHolder(@NonNull View itemView){
            super(itemView);
            title = itemView.findViewById(R.id.ItemTitle);
            quantity=itemView.findViewById(R.id.ItemQuantity);
        }
    }
}