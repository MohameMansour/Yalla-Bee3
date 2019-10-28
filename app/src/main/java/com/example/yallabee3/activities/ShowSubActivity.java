package com.example.yallabee3.activities;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.SubCategoryAdapter;
import com.example.yallabee3.model.SubCategory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowSubActivity extends AppCompatActivity {

    DatabaseReference myRef;
    List<SubCategory> subCategories = new ArrayList<>();
    SubCategoryAdapter adapter;

    String compid;
    FirebaseAuth auth;
    FirebaseUser user;
    @BindView(R.id.back_productt)
    ImageView backProduct;
    @BindView(R.id.my_toolbar)
    Toolbar myToolbar;

    private String recivedName;
    private String recivedIdForProduct;
    String DeviceLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_sub);
        ButterKnife.bind(this);

        myRef = FirebaseDatabase.getInstance().getReference();

        recivedName = getIntent().getExtras().get("intent_object").toString();
//        Toast.makeText(this, recivedName, Toast.LENGTH_SHORT).show();

        recivedIdForProduct = getIntent().getExtras().get("intent_object").toString();
//        Toast.makeText(this, recivedIdForProduct, Toast.LENGTH_SHORT).show();
        DeviceLang = Resources.getSystem().getConfiguration().locale.getLanguage();

        auth = FirebaseAuth.getInstance();
//        user = auth.getCurrentUser();
//        compid = user.getUid();

//        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
//        DatabaseReference db_ref_Main = fb_db_instance.getReference();
//        DatabaseReference blankRecordReference = db_ref_Main ;
//        DatabaseReference db_ref = blankRecordReference.push();
//        String Id = db_ref.getKey();
        if (DeviceLang.equals("ar")) {
            Query query = FirebaseDatabase.getInstance().getReference("SubCategoryAr").orderByChild("catId").equalTo(recivedName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SubCategory subCategory = snapshot.getValue(SubCategory.class);
//                    Toast.makeText(ShowSubActivity.this, dataSnapshot.getChildrenCount() + "", Toast.LENGTH_SHORT).show();
                        String id = snapshot.getKey();
//                    Toast.makeText(ShowSubActivity.this, "new "+id, Toast.LENGTH_SHORT).show();
                        subCategory.setId(id);
                        subCategories.add(subCategory);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } else {
            Query query = FirebaseDatabase.getInstance().getReference("SubCategory").orderByChild("catId").equalTo(recivedName);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        SubCategory subCategory = snapshot.getValue(SubCategory.class);
//                    Toast.makeText(ShowSubActivity.this, dataSnapshot.getChildrenCount() + "", Toast.LENGTH_SHORT).show();
                        String id = snapshot.getKey();
//                    Toast.makeText(ShowSubActivity.this, "new "+id, Toast.LENGTH_SHORT).show();
                        subCategory.setId(id);
                        subCategories.add(subCategory);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        RecyclerView recyclerView = findViewById(R.id.subcategory_recyclerView);
        adapter = new SubCategoryAdapter(subCategories, ShowSubActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(ShowSubActivity.this));
        recyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.back_productt)
    public void onViewClicked() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
