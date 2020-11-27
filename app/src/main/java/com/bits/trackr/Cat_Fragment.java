package com.bits.trackr;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bits.trackr.Adapter.CategoryAdapter;
import com.bits.trackr.Adapter.TaskAdapter;
import com.bits.trackr.Model.CategoryModel;
import com.bits.trackr.Model.TaskModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;

public class Cat_Fragment extends Fragment {
    private RecyclerView categorysRecyclerView;
    FirebaseFirestore fStore;
    //private TaskAdapter tasksAdapter;
    // private List<TaskModel> toDoModelList;
    FirebaseAuth fAuth;
    FirebaseUser user;
    FloatingActionButton addTask;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference taskRef = db.collection("categorys");
    private CategoryAdapter adapter;
    Intent data;
    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.view=view;
        categorysRecyclerView = view.findViewById(R.id.tasksText);
        super.onCreate(savedInstanceState);
        setUpRecyclerView();
    }
    private void setUpRecyclerView()
    {
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();
        user = fAuth.getCurrentUser();
        Query query = fStore.collection("categorys")
                .document(user.getUid())
                .collection("myCategorys")
                .orderBy("title",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<CategoryModel> options= new FirestoreRecyclerOptions.Builder<CategoryModel>()
                .setQuery(query, CategoryModel.class)
                .build();
        adapter=new CategoryAdapter(options);
        RecyclerView recyclerView=view.findViewById(R.id.categories_card);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
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
                }
            }
            public void onItemClicked(@NonNull RecyclerView.ViewHolder viewHolder){
                Toast.makeText(getContext(), "Item clicked at " + viewHolder.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

}
