package com.example.yallabee3.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.CategoryAdapter;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Categery;
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
import java.util.List;


public class CategeryFragment extends Fragment {

    DatabaseReference myRef;

    List<Categery> categeries = new ArrayList<>();
    CategoryAdapter adapter;

    List<Product> Products = new ArrayList<>();
    ShowAdapter showAdapter;

    List<Product> mobileProduct = new ArrayList<>();
    ShowAdapter mobileAdapter;

    List<Product> clotheProduct = new ArrayList<>();
    ShowAdapter clotheAdapter;

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    RecyclerView recyclerView;

    String recivedcatid;
    Context context;
    private ConstraintLayout carsLayout, mobilesLayout, clothesLayout;

    public CategeryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categery, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();

        carsLayout = view.findViewById(R.id.cars_layout);
        mobilesLayout = view.findViewById(R.id.mobiles_layout);
        clothesLayout = view.findViewById(R.id.clothes_layout);


        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        compid = user.getUid();


        CallCategory();
        CallCars();
        CallMobiles();
        CallClothes();

        context = getActivity().getApplicationContext();

        recyclerView = view.findViewById(R.id.categ_info_recyclerView);


        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();

//        RecyclerView recyclerView = view.findViewById(R.id.categ_info_recyclerView);
//        adapter = new ShowAdapter(products, context);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context,
//                LinearLayoutManager.HORIZONTAL, false));
//        recyclerView.setAdapter(adapter);
//        return view;

        RecyclerView recyclerView2 = view.findViewById(R.id.categ2_info_recyclerView);
        showAdapter = new ShowAdapter(Products, context);
        recyclerView2.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView2.setAdapter(showAdapter);


        RecyclerView recyclerView3 = view.findViewById(R.id.categ3_info_recyclerView);
        mobileAdapter = new ShowAdapter(mobileProduct, context);
        recyclerView3.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView3.setAdapter(mobileAdapter);

        RecyclerView recyclerView4 = view.findViewById(R.id.categ4_info_recyclerView);
        clotheAdapter = new ShowAdapter(clotheProduct, context);
        recyclerView4.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView4.setAdapter(clotheAdapter);

        adapter = new CategoryAdapter(categeries, context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void CallCategory() {

        myRef.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categeries.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Categery categery = snapshot.getValue(Categery.class);
                    categeries.add(categery);
                    adapter.notifyDataSetChanged();
                }

//                Collections.reverse(categeries);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void CallCars() {

        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Cars");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Products.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        Products.add(product);
                        showAdapter.notifyDataSetChanged();
                    }
                    carsLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

    public void CallMobiles() {
        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Mobiles");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mobileProduct.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        mobileProduct.add(product);
                        mobileAdapter.notifyDataSetChanged();
                    }
                    mobilesLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CallClothes() {
        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("catId").equalTo("Clothes");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                clotheProduct.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        clotheProduct.add(product);
                        clotheAdapter.notifyDataSetChanged();
                    }
                    clothesLayout.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    }
