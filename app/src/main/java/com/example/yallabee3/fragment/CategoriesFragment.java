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
import com.example.yallabee3.adapt_hold.adapter.CategoryAdapter;
import com.example.yallabee3.model.Categery;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    DatabaseReference myRef;


    List<Categery> categeries = new ArrayList<>();
    CategoryAdapter adapter;
    RecyclerView recyclerView;
    Context context;

    public CategoriesFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories, container, false);

        myRef = FirebaseDatabase.getInstance().getReference();
        context = getActivity().getApplicationContext();

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


        recyclerView = view.findViewById(R.id.categories_info_recyclerView);
        adapter = new CategoryAdapter(categeries, context);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,
                LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);

        return view;
    }

}
