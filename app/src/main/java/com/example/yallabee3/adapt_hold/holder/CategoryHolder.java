package com.example.yallabee3.adapt_hold.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallabee3.R;

public class CategoryHolder extends RecyclerView.ViewHolder {

    public ImageView imageView;
    public TextView nameTextView;

    public CategoryHolder(@NonNull View view) {
        super(view);

        imageView = view.findViewById(R.id.category_img_view);
        nameTextView = view.findViewById(R.id.category_name_textView);

    }
}
