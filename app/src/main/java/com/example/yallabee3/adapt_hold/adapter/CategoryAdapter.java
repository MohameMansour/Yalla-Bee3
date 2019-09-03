package com.example.yallabee3.adapt_hold.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallabee3.R;
import com.example.yallabee3.activities.ShowActivity;
import com.example.yallabee3.adapt_hold.holder.CategoryHolder;
import com.example.yallabee3.model.Categery;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryHolder> {

    private List<Categery> categeries;
    private Context context;

    public final String INTENT_OBJECT = "intentObject";

    private Context getContext() {
        return context;
    }

    public CategoryAdapter(List<Categery> categeries, Context context) {
        this.categeries = categeries;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        final Categery categery = categeries.get(position);

        if (categeries.get(position) != null) {
            Picasso.get()
                    .load(categeries.get(position).getImage())
                    .into(holder.imageView);
        }

        holder.nameTextView.setText(categery.getName());

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ShowActivity.class);

            String name = categery.getName();

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
