package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ShowSubActivity;
import com.example.yallabee3.adapt_hold.holder.CategoryHolder;
import com.example.yallabee3.model.Categery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<Categery> categeries;
    private Context context;

    private int mode;

    public static int LAND_SCAPE_CATEGORY = R.layout.item_category_landscape;

    public final String INTENT_OBJECT = "intentObject";

    private Context getContext() {
        return context;
    }

    public CategoryAdapter(List<Categery> categeries, Context context) {
        this.categeries = categeries;
        this.context = context;
        this.mode = R.layout.item_category;
    }
    public CategoryAdapter(List<Categery> categeries, Context context, int mode) {
        this.categeries = categeries;
        this.context = context;
        this.mode = mode;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(mode, parent, false);
        return new CategoryHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        final Categery categery = categeries.get(position);

        if (categeries.get(position) != null) {
            Picasso.get()
                    .load(categeries.get(position).getImage())
                    .into(holder.imageView);

//            Picasso.get()
//                    .load(categeries.get(position).getImage()).networkPolicy(NetworkPolicy.OFFLINE)
//                    .into(holder.imageView);

        }

        holder.nameTextView.setText(categery.getName());

        holder.itemView.setOnClickListener(v -> {

//    true        Intent intent = new Intent(getContext(), ShowActivity.class);
            Intent intent = new Intent(getContext(), ShowSubActivity.class);


            String name = categery.getName();

            if (name.equals("عربيات وقطع غيار")) {
                    name = "Vehicles";
            } else if (name.equals("عقارات")) {
                name = "Properties";
            } else if (name.equals("موبايلات واكسسواراتها")) {
                name = "Mobile Phones, Accessories";
            } else if (name.equals("الكترونيات وأجهزة منزلية")) {
                name = "Electronics, Home Appliances";
            } else if (name.equals("المنزل والحديقة")) {
                name = "Home, Garden";
            } else if (name.equals("الموضة والجمال")) {
                name = "Fashion, Beauty";
            } else if (name.equals("حيوانات أليفة")) {
                name = "Pets";
            } else if (name.equals("مستلزمات أطفال")) {
                name = "Kids, Babies";
            } else if (name.equals("دراجات ومعدات رياضية")) {
                name = "Sporting Goods, Bikes";
            } else if (name.equals("موسيقى وفنون وكتب")) {
                name = "Hobbies, Music, Art, Books";
            } else if (name.equals("وظائف")) {
                name = "Jobs";
            } else if (name.equals("تجارة وصناعة")) {
                name = "Business, Industrial";
            } else if (name.equals("خدمات")) {
                name = "Services";
            }

//            Toast.makeText(context, id , Toast.LENGTH_SHORT).show();

            intent.putExtra("intent_object" , name);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return categeries.size();
    }
}
