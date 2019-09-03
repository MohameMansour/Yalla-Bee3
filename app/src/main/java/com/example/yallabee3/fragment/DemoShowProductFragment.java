package com.example.yallabee3.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class DemoShowProductFragment extends Fragment {

    DatabaseReference myRef;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    Context context ;

    public DemoShowProductFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_demo_show_product, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        compid = user.getUid();

        context = getActivity().getApplicationContext();

        myRef.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
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

//        RecyclerView recyclerView = view.findViewById(R.id.categ_info_recyclerView);
//        adapter = new ShowAdapter(products, context);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setAdapter(adapter);
//        return view;


        RecyclerView recyclerView = view.findViewById(R.id.product_info_recyclerView);
        adapter = new ShowAdapter(products, context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2 ,
                LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;

    }

}
