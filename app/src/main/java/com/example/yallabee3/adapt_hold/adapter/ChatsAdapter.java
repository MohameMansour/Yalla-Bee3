package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ChatActivity;
import com.example.yallabee3.adapt_hold.holder.ChatsHolder;
import com.example.yallabee3.model.Chats;
import com.example.yallabee3.model.Product;
import com.example.yallabee3.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.support.constraint.Constraints.TAG;

public class ChatsAdapter extends RecyclerView.Adapter<ChatsHolder> {

    private List<Chats> chats;
    private Context context;


    private Context getContext() {
        return context;
    }

    public ChatsAdapter(List<Chats> chats, Context context) {
        this.chats = chats;
        this.context = context;
    }

    @NonNull
    @Override
    public ChatsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);
        return new ChatsHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatsHolder holder, int position) {
        final Chats chats1 = chats.get(position);
        holder.chatListdateTextView.setText(chats1.getDate() +" " + chats1.getTime());
//        holder.chatListdateTextView.setText();
        String chatId = chats1.getRootId();
        String productId = chats1.getProductId();
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("Products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Product product = dataSnapshot.getValue(Product.class);
                Log.d(TAG, "mansour: " + product.getTitle());

                holder.chatListUserNameTextView.setText(product.getTitle());
                if (product != null) {
                    Picasso.get()
                            .load(product.getProductImageUrl())
                            .into(holder.chatListImageView);
                }

                String ownerProduct = product.getUserId();
                DatabaseReference myReff = FirebaseDatabase.getInstance().getReference();
                myReff.child("User").child(ownerProduct).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User product = dataSnapshot.getValue(User.class);
//                        Log.d(TAG, "mansour: " + product.());

                        holder.chatListMessageTextView.setText(product.getFullName());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

//                holder.chatListUserNameTextView.setText(product.getTitle());
                holder.itemView.setOnClickListener(v -> {
                    Intent i = new Intent(getContext(), ChatActivity.class);
                    i.putExtra("productId_key", product.getId());
                    i.putExtra("userId_key", product.getUserId());
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getContext().startActivity(i);
                });
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

//    public void addItem(Chats chats1) {
//        chats.add(chats1);
//        notifyDataSetChanged();
//    }
}
