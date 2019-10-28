package com.example.yallabee3.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallabee3.R;
import com.example.yallabee3.adapt_hold.adapter.ShowAdapter;
import com.example.yallabee3.model.Favourite;
import com.example.yallabee3.model.Product;
import com.example.yallabee3.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    DatabaseReference myRef;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;

    Product product;
    String compid;
    FirebaseAuth auth;
    FirebaseUser user;
    LinearLayout linearrr;
    @BindView(R.id.product_details_imageView)
    ImageView productDetailsImageView;
    @BindView(R.id.product_details_name_textview)
    TextView productDetailsNameTextview;
    @BindView(R.id.product_details_desc_textview)
    TextView productDetailsDescTextview;
    @BindView(R.id.product_details_price_textview)
    TextView productDetailsPriceTextview;
    @BindView(R.id.product_details_location_textview)
    TextView productDetailslocationTextview;
    @BindView(R.id.product_details_call_button)
    ImageView callButton;
    @BindView(R.id.product_details_chat_button)
    ImageView chatButton;
    @BindView(R.id.linear_product_datails)
    LinearLayout linear;
    @BindView(R.id.user_profile_add_product_linear)
    LinearLayout userProfileAddProductLinear;
    @BindView(R.id.product_user_profile_image)
    CircleImageView productUserProfileImage;
    @BindView(R.id.product_user_name_textview)
    TextView productUserNameTextview;
    @BindView(R.id.report_product_textview)
    ImageView reportProductTextview;
    //    @BindView(R.id.favourite_product_image)
//    ImageView favouriteProductImage;
    @BindView(R.id.edit_product_textview)
    TextView editProductTextview;

    String phone, userId, productId;
    String image;
    DatabaseReference databaseReference;
    FirebaseDatabase database;
    String title, desc, price, location, date, area;
    @BindView(R.id.delete_product_textview)
    TextView deleteProductTextview;
    @BindView(R.id.date_product_textview)
    TextView dateProductTextview;
    @BindView(R.id.favourite_product_image_new)
    CheckBox favouriteProductImageNew;
    RecyclerView recyclerViewsuggest;
    @BindView(R.id.vieeee)
    View vieeee;
    @BindView(R.id.sugest_text)
    TextView sugestText;
    @BindView(R.id.suggest_see_text)
    TextView suggestSeeText;
    String currentcountryfromfirebase;
    @BindView(R.id.aaddproduct_3omla_edittext)
    TextView aaddproduct3omlaEdittext;
    @BindView(R.id.aaddproduct_3omlaegy_edittext)
    TextView aaddproduct3omlaegyEdittext;
    @BindView(R.id.aaddproduct_3omlasod_edittext)
    TextView aaddproduct3omlasodEdittext;
    @BindView(R.id.aaddproduct_3omlaema_edittext)
    TextView aaddproduct3omlaemaEdittext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);
        linearrr = findViewById(R.id.linearrr);
        Intent i = this.getIntent();
        recyclerViewsuggest = findViewById(R.id.suggest_products_recyclerView);
        myRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        favouriteProductImageNew.setOnCheckedChangeListener(this);

        SharedPreferences sp = getSharedPreferences("SP_FIREBASE", MODE_PRIVATE);
        currentcountryfromfirebase = sp.getString("countryfromdatabase", "new");


        image = i.getExtras().getString("image_key");
        title = i.getExtras().getString("name_key");
        desc = i.getExtras().getString("desc_key");
        price = i.getExtras().getString("price_key");
        phone = i.getExtras().getString("phone_key");
        location = i.getExtras().getString("location_key");
        userId = i.getExtras().getString("userId_key");
        productId = i.getExtras().getString("productId_key");
        date = i.getExtras().getString("dateId_key");
        area = i.getExtras().getString("areaId_key");

        if (currentcountryfromfirebase.equals("مصر")) {
            aaddproduct3omlaegyEdittext.setVisibility(View.VISIBLE);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            aaddproduct3omlasodEdittext.setVisibility(View.VISIBLE);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            aaddproduct3omlaemaEdittext.setVisibility(View.VISIBLE);
        } else {
            aaddproduct3omlaEdittext.setVisibility(View.VISIBLE);
        }

//        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (userId.equals(user.getUid())) {
            // momken azhrlo mslan eno y3del fe el product bta3o
            editProductTextview.setVisibility(View.VISIBLE);
            deleteProductTextview.setVisibility(View.VISIBLE);
        } else {
            getsuggest();
            linear.setVisibility(View.VISIBLE);
            sugestText.setVisibility(View.VISIBLE);
            suggestSeeText.setVisibility(View.VISIBLE);
            userProfileAddProductLinear.setVisibility(View.VISIBLE);
//            favouriteProductImage.setVisibility(View.VISIBLE);
            myRef.child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);
                    String name = user1.getFullName();
                    String imagee = user1.getImgUrl();


                    if (imagee != null) {
                        Picasso.get()
                                .load(imagee)
                                .into(productUserProfileImage);
                    }else {}
                    productUserNameTextview.setText(name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        if (image != null) {
            Picasso.get()
                    .load(image)
                    .into(productDetailsImageView);
        }
        productDetailsNameTextview.setText(title);
        productDetailsDescTextview.setText(desc);
        productDetailsPriceTextview.setText(price);
        productDetailslocationTextview.setText(location + "-" + area);
        dateProductTextview.setText(date);


    }

    @OnClick({R.id.product_details_call_button, R.id.product_details_chat_button, R.id.report_product_textview, R.id.user_profile_add_product_linear, R.id.product_details_imageView, R.id.edit_product_textview, R.id.delete_product_textview})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.product_details_call_button:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
            case R.id.product_details_chat_button:
                Intent i = new Intent(ProductDetailsActivity.this, ChatActivity.class);
                i.putExtra("userId_key", userId);
                i.putExtra("anotheruserId_key", userId);
                i.putExtra("productId_key", productId);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            case R.id.report_product_textview:
                Intent x = new Intent(ProductDetailsActivity.this, ReportActivity.class);
                x.putExtra("productId_key", productId);
                x.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(x);
                break;
            case R.id.user_profile_add_product_linear:
                Intent c = new Intent(ProductDetailsActivity.this, AnotherProfileActivity.class);
                c.putExtra("userId_key", userId);
                c.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(c);
                break;
//            case R.id.favourite_product_image:
//                database = FirebaseDatabase.getInstance();
//                databaseReference = database.getReference();
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
//                DatabaseReference db_ref_Main = fb_db_instance.getReference();
//                DatabaseReference blankRecordReference = db_ref_Main;
//                DatabaseReference db_ref = blankRecordReference.push();
//                String Id = db_ref.getKey();
//
//                String ownerProfileId = user.getUid();
//                Favourite favourite = new Favourite(Id, productId, ownerProfileId);
//                databaseReference.child("Favourite").child(Id).setValue(favourite);
//                Toast.makeText(this, "Added To Your Favourite", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.product_details_imageView:
                Intent z = new Intent(ProductDetailsActivity.this, DisplayImageActivity.class);
                z.putExtra("imageId_key", image);
                z.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(z);
                break;
            case R.id.edit_product_textview:
                Intent v = new Intent(ProductDetailsActivity.this, EditroductActivity.class);
                v.putExtra("productIds_key", productId);
                v.putExtra("imageId_key", image);
                v.putExtra("userId_key", userId);
                v.putExtra("titleId_key", title);
                v.putExtra("descId_key", desc);
                v.putExtra("phoneId_key", phone);
                v.putExtra("locationId_key", location);
                v.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(v);
                break;
            case R.id.delete_product_textview:
                dialog();
                break;
        }
    }

    private void dialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Delete Your Product ?").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (currentcountryfromfirebase.equals("مصر")) {
                    myRef.child("ProductsEgy").child(productId).removeValue();
                } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
                    myRef.child("ProductsSod").child(productId).removeValue();
                } else if (currentcountryfromfirebase.equals("الامارات")) {
                    myRef.child("ProductsEm").child(productId).removeValue();
                } else {
                    myRef.child("Products").child(productId).removeValue();
                }
//                myRef.child("Products").child(productId).removeValue();
                Intent i = new Intent(ProductDetailsActivity.this, NavActivity.class);
                startActivity(i);
                finish();
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
            DatabaseReference db_ref_Main = fb_db_instance.getReference();
            DatabaseReference blankRecordReference = db_ref_Main;
            DatabaseReference db_ref = blankRecordReference.push();
//                Id = db_ref.getKey();
            String ownerProfileId = user.getUid();
//                Favourite favourite = new Favourite(Id, product.getId(), ownerProfileId);
            Favourite favourite = new Favourite(productId, ownerProfileId);
            databaseReference.child("Favouritee").child(ownerProfileId).child(productId).setValue(favourite);
            Toast.makeText(this, "Added To Your Favourite", Toast.LENGTH_SHORT).show();
        } else {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference();
            String ownerProfileId = user.getUid();
            databaseReference.child("Favouritee").child(ownerProfileId).child(productId).removeValue();
            Toast.makeText(this, "Removed From Favourite", Toast.LENGTH_SHORT).show();
        }
    }

    private void getsuggest() {
        Query query;
        if (currentcountryfromfirebase.equals("مصر")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEgy").orderByChild("userId").equalTo(userId).limitToLast(8);
        } else if (currentcountryfromfirebase.equals("السعودية العربية")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsSod").orderByChild("userId").equalTo(userId).limitToLast(8);
        } else if (currentcountryfromfirebase.equals("الامارات")) {
            query = FirebaseDatabase.getInstance().getReference("ProductsEm").orderByChild("userId").equalTo(userId).limitToLast(8);
        } else {
            query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(userId).limitToLast(8);
        }
//        Query query = FirebaseDatabase.getInstance().getReference("Products").orderByChild("userId").equalTo(userId).limitToLast(8);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                products.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Product product = snapshot.getValue(Product.class);
                        products.add(product);
                        adapter.notifyDataSetChanged();
                    }
                    Collections.reverse(products);
                    linearrr.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        adapter = new ShowAdapter(products, this);
        recyclerViewsuggest.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerViewsuggest.setAdapter(adapter);

    }

    @OnClick(R.id.suggest_see_text)
    public void onViewClicked() {
        Intent c = new Intent(ProductDetailsActivity.this, AnotherProfileActivity.class);
        c.putExtra("userId_key", userId);
        c.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(c);

    }
}
