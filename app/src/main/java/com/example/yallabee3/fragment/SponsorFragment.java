package com.example.yallabee3.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.AddSponsorActivity;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.adapt_hold.adapter.SponserAdapter;
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
import java.util.Objects;


public class SponsorFragment extends Fragment {

    private RecyclerView popularCoursesRecyclerView;
    private Button addsponorbutton;
    private Context context;

    private SponserAdapter sponserAdapter;

    DatabaseReference myRef;
    List<Product> products = new ArrayList<>();

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    public SponsorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sponsor, container, false);
        context = Objects.requireNonNull(getActivity()).getApplicationContext();

        myRef = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        compid = user.getUid();

        addsponorbutton = view.findViewById(R.id.addsponsor_button_add);
        addsponorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context , AddSponsorActivity.class);
                startActivity(i);
            }
        });

        myRef.child("Sponsor").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Product product = snapshot.getValue(Product.class);
                    products.add(product);
                    sponserAdapter.notifyDataSetChanged();
                }

                Collections.reverse(products);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        popularCoursesRecyclerView = view.findViewById(R.id.home_popularCourses_recycler_view);
        sponserAdapter = new SponserAdapter(products, context);
        popularCoursesRecyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        popularCoursesRecyclerView.setAdapter(sponserAdapter);

        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            assert getFragmentManager() != null;
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }

}
