package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ChatActivity;
import com.example.yallabee3.adapt_hold.holder.ChatListHolder;
import com.example.yallabee3.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ChatListAdapter extends RecyclerView.Adapter<ChatListHolder> {

    private List<Product> products;
    private Context context;


    private Context getContext() {
        return context;
    }

    public ChatListAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
    }

//    public ChatListAdapter(List<User> users, Context context, int mode) {
//        this.users = users;
//        this.context = context;
//        this.mode = mode;
//    }


    @NonNull
    @Override
    public ChatListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list, parent, false);
        return new ChatListHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatListHolder holder, int position) {

//        final Chat product = products.get(position);
//
//        holder.chatListUserNameTextView.setText(product.getId());
//
//        holder.itemView.setOnClickListener(v -> {
//                    Intent i = new Intent(getContext(), ChatActivity.class);
//                    i.putExtra("productId_key", product.getId());
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    getContext().startActivity(i);
//            });

        final Product product = products.get(position);
//
        if (products.get(position) != null) {
            Picasso.get()
                    .load(products.get(position).getProductImageUrl())
                    .into(holder.chatListImageView);
        }

        holder.chatListUserNameTextView.setText(product.getTitle());

        holder.itemView.setOnClickListener(v -> {

//            Intent i = new Intent(getContext(), ChatFragment.class);
//            i.putExtra("productId_key", product.getId());
//            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getContext().startActivity(i);

            Intent i = new Intent(getContext(), ChatActivity.class);
            i.putExtra("productId_key", product.getId());
            i.putExtra("userId_key", product.getUserId());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(i);

//
//            ChatFragment fragmentB=new ChatFragment();
//            Bundle bundle=new Bundle();
//            bundle.putString("NAME",product.getId());
//            fragmentB.setArguments(bundle);
//
//        });

        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
