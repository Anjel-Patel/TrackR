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
import android.widget.TextView;
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


public class EditItem extends AppCompatActivity {
    Intent data;
    TextView editItemTitle;
    EditText editItemQuantity;
    FirebaseFirestore fStore;
    FirebaseUser user;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_item);
        data=getIntent();
        fStore=FirebaseFirestore.getInstance();
        editItemTitle = findViewById(R.id.editItemTitle);
        editItemQuantity = findViewById(R.id.editItemQuantity);
        user = FirebaseAuth.getInstance().getCurrentUser();
        editItemTitle.setText(data.getStringExtra("title"));
        editItemQuantity.setText(data.getStringExtra("quantity"));
        Button edititem=findViewById(R.id.editedItemItemButton);
        edititem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Quantity=editItemQuantity.getText().toString().trim();
                if(Quantity.isEmpty()||!Quantity.matches("\\d+")){
                    Toast.makeText(EditItem.this,"Cannot save empty/non-numeric quantity",Toast.LENGTH_SHORT).show();
                    return;
                }
                DocumentReference docref=fStore.collection("categorys").
                        document(user.getUid()).
                        collection("myCategorys").
                        document(data.getStringExtra("categoryid"))
                        .collection("myItems")
                        .document(data.getStringExtra("itemid"));

                Map<String,Object> item = new HashMap<>();
                item.put("quantity",Quantity);

                docref.update(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(EditItem.this,"Quantity Updated",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),CategoryContent.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditItem.this,"Quantity not updated, try again",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
