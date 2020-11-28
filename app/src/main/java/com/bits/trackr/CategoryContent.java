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

import com.bits.trackr.Adapter.ItemAdapter;
import com.bits.trackr.Adapter.TaskAdapter;
import com.bits.trackr.Model.ItemModel;
import com.bits.trackr.Model.TaskModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;

public class CategoryContent extends AppCompatActivity {

    private RecyclerView ItemsRecyclerView;
    FirebaseFirestore fStore;
    //private TaskAdapter tasksAdapter;
    // private List<TaskModel> toDoModelList;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FloatingActionButton addItem;
    Intent data;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    private CollectionReference categoryRef;
    private ItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        ItemsRecyclerView = findViewById(R.id.itemstext);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_content);
        addItem=findViewById(R.id.addNewItem);
        data=getIntent();
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoryContent.this, AddItem.class);
                i.putExtra("categoryid",data.getStringExtra("categoryid"));
                v.getContext().startActivity(i);
            }
        });
        setUpRecyclerView();
    }
    private void setUpRecyclerView()
    {
        data=getIntent();
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        Query query = fStore.collection("categorys")
                .document(user.getUid())
                .collection("myCategorys")
                .document(data.getStringExtra("categoryid"))
                .collection("myItems")
                .orderBy("title",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<ItemModel> options= new FirestoreRecyclerOptions.Builder<ItemModel>()
                .setQuery(query, ItemModel.class)
                .build();
        adapter=new ItemAdapter(options);
        RecyclerView recyclerView=findViewById(R.id.itemstext);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setCategoryId(data.getStringExtra("categoryid"));
        recyclerView.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
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
                    Toast.makeText(CategoryContent.this,"Item Deleted",Toast.LENGTH_SHORT);
                }
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
