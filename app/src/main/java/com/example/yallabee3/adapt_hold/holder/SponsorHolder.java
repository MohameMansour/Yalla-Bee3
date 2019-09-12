package com.example.yallabee3.adapt_hold.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallabee3.R;

public class SponsorHolder extends RecyclerView.ViewHolder{

    public CardView layout;
    public ImageView imageView;
    public TextView titlesponsorTextView;
    public TextView dessponsorTextView;
    public TextView costsponsotTextView;
    public TextView phonesponsotTextView;
    public TextView locationsponsotTextView;
    public TextView timesponsotTextView;


    public SponsorHolder(@NonNull View view) {
        super(view);
        layout = view.findViewById(R.id.item_course_cardView);
        imageView = view.findViewById(R.id.item_course_imageView);
        titlesponsorTextView = view.findViewById(R.id.item_course_title_textView);
        dessponsorTextView = view.findViewById(R.id.item_course_instructor_name_textView);
        costsponsotTextView = view.findViewById(R.id.item_course_duration_textView);
        phonesponsotTextView =view.findViewById(R.id.item_phone_textView);
        locationsponsotTextView =view.findViewById(R.id.item_location_textView);
        timesponsotTextView =view.findViewById(R.id.item_time_textView);
    }
}
