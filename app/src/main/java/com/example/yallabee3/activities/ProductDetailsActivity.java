package com.example.yallabee3.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProductDetailsActivity extends AppCompatActivity {


    DatabaseReference myRef;
    List<Product> products = new ArrayList<>();
    ShowAdapter adapter;

    Product product;
    String compid;
    FirebaseAuth auth;
    FirebaseUser user;

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
    Button callButton;
    @BindView(R.id.product_details_chat_button)
    Button chatButton;
    @BindView(R.id.linear_product_datails)
    LinearLayout linear;
    @BindView(R.id.user_profile_add_product_linear)
    LinearLayout userProfileAddProductLinear;
    @BindView(R.id.product_user_profile_image)
    CircleImageView productUserProfileImage;
    @BindView(R.id.product_user_name_textview)
    TextView productUserNameTextview;
    @BindView(R.id.report_product_textview)
    TextView reportProductTextview;
    @BindView(R.id.favourite_product_image)
    ImageView favouriteProductImage;

    String phone, userId, productId;

    DatabaseReference databaseReference;
    FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        ButterKnife.bind(this);

        Intent i = this.getIntent();

        myRef = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();

        String image = i.getExtras().getString("image_key");
        String title = i.getExtras().getString("name_key");
        String desc = i.getExtras().getString("desc_key");
        String price = i.getExtras().getString("price_key");
        phone = i.getExtras().getString("phone_key");
        String location = i.getExtras().getString("location_key");
        userId = i.getExtras().getString("userId_key");
        productId = i.getExtras().getString("productId_key");

        Toast.makeText(this, userId, Toast.LENGTH_SHORT).show();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (userId.equals(user.getUid())) {
            // momken azhrlo mslan eno y3del fe el product bta3o
        } else {
            linear.setVisibility(View.VISIBLE);
            reportProductTextview.setVisibility(View.VISIBLE);
            userProfileAddProductLinear.setVisibility(View.VISIBLE);
            favouriteProductImage.setVisibility(View.VISIBLE);
            myRef.child("User").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    User user1 = dataSnapshot.getValue(User.class);
                    String name = user1.getFullName();
                    String image = user1.getImgUrl();


                    if (image != null) {
                        Picasso.get()
                                .load(image)
                                .into(productUserProfileImage);
                    }
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
        productDetailslocationTextview.setText(location);


    }

    @OnClick({R.id.product_details_call_button, R.id.product_details_chat_button, R.id.report_product_textview, R.id.user_profile_add_product_linear})
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
        }
    }

    @OnClick(R.id.favourite_product_image)
    public void onViewClicked() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase fb_db_instance = FirebaseDatabase.getInstance();
        DatabaseReference db_ref_Main = fb_db_instance.getReference();
        DatabaseReference blankRecordReference = db_ref_Main;
        DatabaseReference db_ref = blankRecordReference.push();
        String Id = db_ref.getKey();

        String ownerProfileId = user.getUid();
        Favourite favourite = new Favourite(Id, productId, ownerProfileId);
        databaseReference.child("Favourite").child(Id).setValue(favourite);
        Toast.makeText(this, "Added To Your Favourite", Toast.LENGTH_SHORT).show();

    }
}
