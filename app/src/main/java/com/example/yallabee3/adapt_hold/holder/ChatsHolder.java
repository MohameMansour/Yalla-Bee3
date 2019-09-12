package com.example.yallabee3.adapt_hold.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallabee3.R;

public class ChatsHolder extends RecyclerView.ViewHolder{

    public ImageView chatListImageView;
    public TextView chatListUserNameTextView;
    public TextView chatListMessageTextView;
    public TextView chatListdateTextView;

    public ChatsHolder(@NonNull View view) {
        super(view);

        chatListImageView = view.findViewById(R.id.chat_list_img_view);
        chatListUserNameTextView = view.findViewById(R.id.chat_list_name_textView);
        chatListMessageTextView = view.findViewById(R.id.chat_list_message_textView);
        chatListdateTextView = view.findViewById(R.id.chat_date_textView);

    }
}
