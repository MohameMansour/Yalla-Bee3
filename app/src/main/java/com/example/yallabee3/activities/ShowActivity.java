package com.example.yallabee3.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowActivity extends AppCompatActivity {

    DatabaseReference myRef;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    private String recivedName ;
    private String recivedIdForProduct ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        myRef = FirebaseDatabase.getInstance().getReference();

        recivedName = getIntent().getExtras().get("intent_object").toString();
        Toast.makeText(this, recivedName, Toast.LENGTH_SHORT).show();

        recivedIdForProduct = getIntent().getExtras().get("intent_object").toString();
        Toast.makeText(this, recivedIdForProduct, Toast.LENGTH_SHORT).show();

        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        compid = user.getUid();

//        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
//        DatabaseReference db_ref_Main = fb_db_instance.getReference();
//        DatabaseReference blankRecordReference = db_ref_Main ;
//        DatabaseReference db_ref = blankRecordReference.push();
//        String Id = db_ref.getKey();

        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo(recivedName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    products.add(product);
                    adapter.notifyDataSetChanged();
                }

                Collections.reverse(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


//        myRef.child("Category").child(recivedName).child(recivedName).child(recivedIdForProduct).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                products.clear();
//
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Product product = snapshot.getValue(Product.class);
//                    products.add(product);
//                    adapter.notifyDataSetChanged();
//                }
//
//                Collections.reverse(products);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        RecyclerView recyclerView = findViewById(R.id.driver_info_recyclerView);
        adapter = new ShowAdapter(products, ShowActivity.this , ShowAdapter.LAND_SCAPE_PRODUCT);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowActivity.this));
        recyclerView.setAdapter(adapter);
    }
}
