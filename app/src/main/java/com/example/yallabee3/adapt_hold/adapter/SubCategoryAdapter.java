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
import com.example.yallabee3.adapt_hold.holder.SubCategoryHolder;
import com.example.yallabee3.model.SubCategory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryHolder> {

    private List<SubCategory> subCategories;
    private Context context;

    private Context getContext() {
        return context;
    }

    public SubCategoryAdapter(List<SubCategory> subCategories, Context context) {
        this.subCategories = subCategories;
        this.context = context;
    }

    @NonNull
    @Override
    public SubCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subcategory, parent, false);
        return new SubCategoryHolder(row);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryHolder holder, int position) {
        final SubCategory subCategory = subCategories.get(position);

        holder.subCategoyNameTextView.setText(subCategory.getName());
//        holder.subCategoyNumberTextView.setText(subCategory.getNumberOfProducts());

        String productId = subCategory.getId();

        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("subCatId").equalTo(productId);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                holder.subCategoyNumberTextView.setText(dataSnapshot.getChildrenCount()+ " Item");
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(getContext(), ShowActivity.class);
            String name = subCategory.getName();
            intent.putExtra("intent_object", name);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return subCategories.size();
    }
}
