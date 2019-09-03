package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.holder.MessageHolder;
import com.example.yallabee3.model.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageHolder> {

    private List<Message> messages;
    private Context context;
    FirebaseUser fuser;
    private int mode;
    String imageUrl;

    private static final int LEFt_MESSAGE = 0;
    private static final int RIGHT_MESSAGE = 1;

    private Context getContext() {
        return context;
    }

    public MessageAdapter(List<Message> messages, Context context, String imageUrl) {
        this.messages = messages;
        this.context = context;
        this.imageUrl = imageUrl;
    }

    public MessageAdapter(List<Message> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }

    @NonNull
    @Override
    public MessageHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        if (i == RIGHT_MESSAGE) {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_right_blue, parent, false);
            return new MessageHolder(row);
        } else {
            View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_left_white, parent, false);
            return new MessageHolder(row);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MessageHolder holder, int i) {

        String message = messages.get(i).getMessage();

        holder.messageTextView.setText(message);

        if (messages.get(i) != null) {
            Picasso.get()
                    .load(imageUrl)
                    .into(holder.profileImageView);
        }
        if (i == messages.size() - 1) {
            if (messages.get(i).isSeen()) {
                holder.isSeenTextView.setText("Seen");
            } else {
                holder.isSeenTextView.setText("Delivered");
            }
        } else {
            holder.isSeenTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getSender().equals(fuser.getUid())) {
            return RIGHT_MESSAGE;
        } else {
            return LEFt_MESSAGE;
        }
    }


}
