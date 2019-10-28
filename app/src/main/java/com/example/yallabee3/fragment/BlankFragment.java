package com.example.yallabee3.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.yallabee3.IFirebaseLoadListener;
import com.example.yallabee3.MyItemGroupAdapter;
import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.CategoryAdapter;
import com.example.yallabee3.model.Categery;
import com.example.yallabee3.model.ItemData;
import com.example.yallabee3.model.ItemGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BlankFragment extends Fragment implements IFirebaseLoadListener {

    IFirebaseLoadListener iFirebaseLoadListener;
    RecyclerView my_recycler_view;
    DatabaseReference myData;
    Context c;

    List<Categery> categeries = new ArrayList<>();
    CategoryAdapter adapter;
    DatabaseReference myRef;


    public BlankFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);

        myData = FirebaseDatabase.getInstance().getReference("Products");
        iFirebaseLoadListener = this;

        my_recycler_view = view.findViewById(R.id.my_recyclerView);
        my_recycler_view.setHasFixedSize(true);
        my_recycler_view.setLayoutManager(new LinearLayoutManager(c));

        getFirebaseData();
        return view;
    }

    private void getFirebaseData() {
        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Category").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categeries.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Categery categery = snapshot.getValue(Categery.class);


                    String id = snapshot.getKey();
                    assert categery != null;
                    categery.setId(id);

                    String namecat = categery.getId();

                    //
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                            Product product = dataSnapshot.getValue(Product.class);
//                            products.add(product);
//                            showAdapter.notifyDataSetChanged();
////                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });

                    Query query = FirebaseDatabase.getInstance().getReference("Products")
                            .child("-LoKK0xhnYlc15oft-kL").orderByChild("catId").equalTo("Vehicles");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            List<ItemGroup> itemGroups = new ArrayList<>();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                ItemGroup itemGroup = snapshot.getValue(ItemGroup.class);

                                itemGroup.setHeaderTitle(snapshot.child("headerTitle").getValue(true).toString());
                                GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>() {};
                                itemGroup.setListItem(snapshot.child("listItem").getValue(genericTypeIndicator));
                                itemGroups.add(itemGroup);

                            }
                            iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            iFirebaseLoadListener.onFirebaseFailed(databaseError.getMessage());
                        }
                    });
//                    myData.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            List<ItemGroup> itemGroups = new ArrayList<>();
//
//                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                ItemGroup itemGroup = snapshot.getValue(ItemGroup.class);
//
//                                itemGroup.setHeaderTitle(snapshot.child("headerTitle").getValue(true).toString());
//                                GenericTypeIndicator<ArrayList<ItemData>> genericTypeIndicator = new GenericTypeIndicator<ArrayList<ItemData>>() {};
//                                itemGroup.setListItem(snapshot.child("listItem").getValue(genericTypeIndicator));
//                                itemGroups.add(itemGroup);
//
//                            }
//                            iFirebaseLoadListener.onFirebaseLoadSuccess(itemGroups);
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            iFirebaseLoadListener.onFirebaseFailed(databaseError.getMessage());
//                        }
//                    });
                    //
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    @Override
    public void onFirebaseLoadSuccess(List<ItemGroup> itemGroupList) {
        MyItemGroupAdapter adapter = new MyItemGroupAdapter(c , itemGroupList);
        my_recycler_view.setAdapter(adapter);
    }
    @Override
    public void onFirebaseFailed(String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
