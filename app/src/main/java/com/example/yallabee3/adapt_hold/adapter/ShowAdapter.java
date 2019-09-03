package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ProductDetailsActivity;
import com.example.yallabee3.adapt_hold.holder.ShowHolder;
import com.example.yallabee3.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShowAdapter extends RecyclerView.Adapter<ShowHolder> {

    private List<Product> products;
    private Context context;

    private int mode;

    public static int LAND_SCAPE_PRODUCT = R.layout.item_show_landscape;

    private Context getContext() {
        return context;
    }

    public ShowAdapter(List<Product> products, Context context) {
        this.products = products;
        this.context = context;
        this.mode = R.layout.item_show;

    }

    public ShowAdapter(List<Product> products, Context context, int mode) {
        this.products = products;
        this.context = context;
        this.mode = mode;
    }

    @NonNull
    @Override
    public ShowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(mode, parent, false);
        return new ShowHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowHolder holder, int position) {

        final Product product = products.get(position);

        if (products.get(position) != null) {
            Picasso.get()
                    .load(products.get(position).getProductImageUrl())
                    .into(holder.imageView);
        }

        holder.nameTextView.setText(product.getTitle());
        holder.descTextView.setText(product.getDescription());
        holder.priceTextView.setText(product.getPrice());
        holder.locationTextView.setText(product.getPlace());

        holder.itemView.setOnClickListener(v -> {
            openDetails(product.getProductImageUrl(), product.getTitle(),product.getDescription()
                    , product.getPrice(), product.getPhone() ,product.getPlace() , product.getUserId());
//            Intent intent = new Intent(getContext(), ProductDetailsActivity.class);

//            String id = product.getId();

//            Toast.makeText(context, id , Toast.LENGTH_SHORT).show();

//            intent.putExtra("intent_object" ,);

//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            getContext().startActivity(intent);
        });


    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    private void openDetails(String... details) {

        Intent i = new Intent(getContext(), ProductDetailsActivity.class);

        i.putExtra("image_key", details[0]);
        i.putExtra("name_key", details[1]);
        i.putExtra("desc_key", details[2]);
        i.putExtra("price_key", details[3]);
        i.putExtra("phone_key", details[4]);
        i.putExtra("location_key", details[5]);
        i.putExtra("userId_key", details[6]);

        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        getContext().startActivity(i);
    }

}
