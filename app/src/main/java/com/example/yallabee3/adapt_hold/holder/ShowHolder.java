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
    public TextView dateTextView;
//    public CheckBox favimageView;
    public TextView omlaTextView;
    public TextView omlaegyTextView;
    public TextView omlasodTextView;
    public TextView omlaemTextView;


    public ShowHolder(@NonNull View view) {
        super(view);
        imageView = view.findViewById(R.id.product_info_img_view);
        nameTextView = view.findViewById(R.id.product_info_name_textView);
        descTextView=view.findViewById(R.id.product_info_des_textView);
        priceTextView = view.findViewById(R.id.driver_info_price_textView);
        locationTextView = view.findViewById(R.id.driver_info_location_textView);
        dateTextView = view.findViewById(R.id.product_info_date_textView);
//        favimageView = view.findViewById(R.id.favourite_product_image_new);
        omlaTextView =view.findViewById(R.id.aaddproduct_3omla_edittext);
        omlaegyTextView =view.findViewById(R.id.aaddproduct_3omlaegy_edittext);
        omlasodTextView =view.findViewById(R.id.aaddproduct_3omlasod_edittext);
        omlaemTextView =view.findViewById(R.id.aaddproduct_3omlaema_edittext);

    }
}
