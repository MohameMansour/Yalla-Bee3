package com.example.yallabee3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Product;
import com.example.yallabee3.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class AnotherProfileActivity extends AppCompatActivity {

    String userId;
    @BindView(R.id.anotherprofile_image_imageView)
    CircleImageView anotherprofileImageImageView;
    @BindView(R.id.anotherprofile_username_textview)
    TextView anotherprofileUsernameTextview;
    @BindView(R.id.anotherprofile_email_textview)
    TextView anotherprofileEmailTextview;
    @BindView(R.id.anotherprofile_products_textview)
    TextView anotherprofileProductsTextview;
    @BindView(R.id.anotherprofile_products_recyclerView)
    RecyclerView anotherprofileProductsRecyclerView;

    DatabaseReference myRef;
    FirebaseAuth auth;
    String currentcountryfromfirebase;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another_profile);
        ButterKnife.bind(this);

        Intent i = this.getIntent();
        userId = i.getExtras().getString("userId_key");

        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences sp = getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");

        Query query;
        if (currentcountryfromfirebase.equals("مصر") || currentcountryfromfirebase.equals("Egypt")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("userId").equalTo(userId);
        } else if (currentcountryfromfirebase.equals("السعودية العربية") || currentcountryfromfirebase.equals("KSA")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("userId").equalTo(userId);
        } else if (currentcountryfromfirebase.equals("الامارات") || currentcountryfromfirebase.equals("Arab Emirates")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("userId").equalTo(userId);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(userId);
        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(userId);
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

        RecyclerView recyclerView = findViewById(R.id.anotherprofile_products_recyclerView);
        adapter = new ShowAdapter(products, this, ShowAdapter.LAND_SCAPE_PRODUCT);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (user != null) {


            myRef.child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);
                    String name = user1.getFullName();
                    String image = user1.getImgUrl();
                    String email = user1.getEmail();


                    if (image != null) {
                        Picasso.get()
                                .load(image)
                                .into(anotherprofileImageImageView);
                    } else {

                    }
                    anotherprofileUsernameTextview.setText(name);
                    anotherprofileEmailTextview.setText(email);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }


}

