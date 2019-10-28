package com.example.yallabee3.adapt_hold.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.yallabee3.R;

public class SubCategoryHolder extends RecyclerView.ViewHolder {


    public TextView subCategoyNameTextView;
    public TextView subCategoyNumberTextView;

    public SubCategoryHolder(@NonNull View view) {
        super(view);

        subCategoyNameTextView = view.findViewById(R.id.subcategory_name_textView);
        subCategoyNumberTextView = view.findViewById(R.id.subcategory_number_textView);
    }
}
