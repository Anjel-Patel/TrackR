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

public class AddCategory extends AppCompatActivity {
    FirebaseFirestore fstore;
    EditText categoryTitle;
    FirebaseUser user;
    Intent data;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_category);
        data = getIntent();
        fstore = FirebaseFirestore.getInstance();
        categoryTitle = findViewById(R.id.newCategoryTitle);
        user = FirebaseAuth.getInstance().getCurrentUser();
        Button newCategory = findViewById(R.id.addedNewCategoryButton);
        newCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = categoryTitle.getText().toString();
                if(Title.isEmpty()){
                    Toast.makeText(AddCategory.this, "Cannot save Category with Empty Fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                DocumentReference docref = fstore.collection("categorys").document(user.getUid())
                        .collection("myCategorys").document();
                Map<String,Object> category = new HashMap<>();
                category.put("title",Title);
                docref.set(category).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddCategory.this, "Category Added", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddCategory.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
