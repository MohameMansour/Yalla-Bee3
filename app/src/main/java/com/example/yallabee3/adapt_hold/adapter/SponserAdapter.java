package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.DisplayImageActivity;
import com.example.yallabee3.adapt_hold.holder.SponsorHolder;
import com.example.yallabee3.model.Sponsor;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;

public class SponserAdapter extends RecyclerView.Adapter<SponsorHolder> {
    @BindView(R.id.item_course_imageView)
    ImageView itemCourseImageView;
    private List<Sponsor> products;
    private Context context;

    String image;

    private Context getContext() {
        return context;
    }

    public SponserAdapter(List<Sponsor> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public SponsorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sponsor, parent, false);
        return new SponsorHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsorHolder holder, int position) {
        final Sponsor product = products.get(position);



        if (products.get(position) != null) {
            Picasso.get()
                    .load(products.get(position).getProductImageUrl())
                    .into(holder.imageView);
        }

        holder.titlesponsorTextView.setText(product.getTitle());
        holder.dessponsorTextView.setText(product.getDescription());
        holder.costsponsotTextView.setText(product.getPrice());
        holder.phonesponsotTextView.setText(product.getPhoneNumber());
        holder.locationsponsotTextView.setText(product.getLocation());
        holder.timesponsotTextView.setText(product.getTime());
        holder.itemView.setOnClickListener(v ->{

            openImage(product.getProductImageUrl() , product.getPhoneNumber());

        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void openImage(String image , String phone) {
        Intent i = new Intent(getContext(), DisplayImageActivity.class);
        i.putExtra("imageIds_key", image);
        i.putExtra("phoneIds_key", phone);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getContext().startActivity(i);
    }

}
