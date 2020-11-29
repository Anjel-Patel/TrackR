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

public class AddItem extends AppCompatActivity {
    FirebaseFirestore fstore;
    EditText itemQuantity, itemTitle;
    FirebaseUser user;
    Intent data;
    Button newItem;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_item);
        data = getIntent();
        fstore = FirebaseFirestore.getInstance();
        itemQuantity = findViewById(R.id.newItemQuantity);
        itemTitle = findViewById(R.id.newItemTitle);
        user = FirebaseAuth.getInstance().getCurrentUser();
        newItem = findViewById(R.id.addedNewItemButton);
        newItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Title = itemTitle.getText().toString();
                String Quantity = itemQuantity.getText().toString();

                if(Title.isEmpty()||Quantity.isEmpty()){
                    Toast.makeText(AddItem.this, "Cannot save Items with Empty Fields", Toast.LENGTH_SHORT).show();
                    return;

                }
                DocumentReference docref = fstore.collection("categorys").document(user.getUid())
                        .collection("myCategorys").document(data.getStringExtra("categoryid")).collection("myItems").document();
                Map<String,Object> task = new HashMap<>();
                task.put("title",Title);
                task.put("quantity",Quantity);
                docref.set(task).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddItem.this, "Item Added", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddItem.this,CategoryContent.class);
                        intent.putExtra("categoryid", data.getStringExtra("categoryid"));
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItem.this, "Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}