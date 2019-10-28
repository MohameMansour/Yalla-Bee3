package com.example.yallabee3.adapt_hold.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallabee3.R;

public class MessageHolder extends RecyclerView.ViewHolder {

    public ImageView profileImageView;
    public TextView messageTextView, isSeenTextView,dateTextView;

    public MessageHolder(View view) {
        super(view);
        profileImageView = view.findViewById(R.id.image_message);
        messageTextView = view.findViewById(R.id.message_textview);
        isSeenTextView = view.findViewById(R.id.message_seen_textview);
        dateTextView=view.findViewById(R.id.date_textview);
    }
}
