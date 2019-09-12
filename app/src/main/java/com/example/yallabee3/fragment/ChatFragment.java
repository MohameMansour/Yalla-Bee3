package com.example.yallabee3.fragment;


import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ChatsAdapter;
import com.example.yallabee3.model.Chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatFragment extends Fragment {

    DatabaseReference myRef;
    //    List<Product> products = new ArrayList<>();
    List<Chats> chats = new ArrayList<>();

//    ChatListAdapter chatadapter;
    ChatsAdapter chatsadapter ;
//    List<String> chats = new ArrayList<>();

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

    String productId;
    String x;

    public ChatFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

//        Intent i = getActivity().getIntent();
//        productId = i.getExtras().getString("productId_key");

//        String productId = getArguments().getString("NAME");

//        productId = "-Ln7D-WeKBAGezTAvB_U";

        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();

//
//        myRef.child("Chat").addListenerForSingleValueEvent(new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
////                products.clear();
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Chat chat = snapshot.getValue(Chat.class);
//
//                    chats.add(snapshot.getKey());
//
//                        x = chats.toString();
////                    String key = myRef.child("Chat").push().getKey();
////                    String chatId = snapshot.getKey();
//
//                        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("id").equalTo(x);
//                        query.addListenerForSingleValueEvent(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                                products.clear();
//
//                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                                    Product product = snapshot.getValue(Product.class);
//                                    products.add(product);
//                                    chatadapter.notifyDataSetChanged();
//                                }
//
//                                Collections.reverse(products);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                            }
//                        });
//
//                    RecyclerView recyclerView = view.findViewById(R.id.chatlist_recyclerview);
//                    chatadapter = new ChatListAdapter(products, getContext());
//                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//                    recyclerView.setAdapter(chatadapter);

//                    Chat chat = snapshot.getValue(Chat.class);
//                    products.add(chat);
//                    adapter.notifyDataSetChanged();

//                    Toast.makeText(getContext(), "id :" + snapshot.getValue(), Toast.LENGTH_SHORT).show();
//                    products.add();
//                    adapter.notifyDataSetChanged();

//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//
//        });

//            Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("id").equalTo(productId);
//        Query query = FirebaseDatabase.getInstance().getReference("Chats").equalTo(productId);
//
////        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("id").equalTo(productId);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            //        myRef.child("Chat").child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
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

//
//        RecyclerView recyclerView = view.findViewById(R.id.chatlist_recyclerview);
//        chatadapter = new ChatListAdapter(products, getContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setAdapter(chatadapter);

        myRef.child("Chats").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Chats chats1 = snapshot.getValue(Chats.class);
                    String id = snapshot.getKey();
                    assert chats1 != null;
                    chats1.setRootId(id);
                    //
                    String totaltime = null;
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat gethour = new SimpleDateFormat("HH");
                    SimpleDateFormat getminute = new SimpleDateFormat("mm");
                    String hour = gethour.format(c.getTime());
                    String minute = getminute.format(c.getTime());
                    int convertedVal = Integer.parseInt(hour);

                    if (convertedVal > 12) {
                        totaltime = ((convertedVal - 12) + ":" + (minute) + "pm");
                    } else if (convertedVal == 12) {
                        totaltime = ("12" + ":" + (minute) + "pm");
                    } else if (convertedVal < 12) {
                        if (convertedVal != 0) {
                            totaltime = ((convertedVal) + ":" + (minute) + "am");
                        } else {
                            totaltime = ("12" + ":" + minute + "am");
                        }
                    }
                    //
                    String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());

                    //
                    chats1.setDate(date_n);
                    chats1.setTime(totaltime);
                    chats.add(chats1);
//                    chatsadapter.addItem(chats1);
                    chatsadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        RecyclerView recyclerView = view.findViewById(R.id.chatlist_recyclerview);
        chatsadapter = new ChatsAdapter(chats, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatsadapter);

        return view;
    }

//    private void getProductbyId(String productId) {
//        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
//        myRef.child("Products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                    Chats chats1 = snapshot.getValue(Chats.class);
//                    String id = snapshot.getKey();
//                    Log.d(TAG, "id: " + id);
//                    chats.add(chats1);
//                    Log.d(TAG, "onDataChange: " + chats1);
//                }
//            }
//
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }


}
