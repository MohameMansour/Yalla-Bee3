package com.example.yallabee3.adapt_hold.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallabee3.R;

public class ShowHolder extends RecyclerView.ViewHolder{

    public ImageView imageView;
    public TextView nameTextView;
    public TextView descTextView;
    public TextView priceTextView;
    public TextView locationTextView;

    public ShowHolder(@NonNull View view) {
        super(view);
        imageView = view.findViewById(R.id.product_info_img_view);
        nameTextView = view.findViewById(R.id.product_info_name_textView);
        descTextView=view.findViewById(R.id.product_info_des_textView);
        priceTextView = view.findViewById(R.id.driver_info_price_textView);
        locationTextView = view.findViewById(R.id.driver_info_location_textView);
    }
}
