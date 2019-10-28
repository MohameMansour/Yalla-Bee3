package com.example.yallabee3.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.yallabee3.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayImageActivity extends AppCompatActivity {

    @BindView(R.id.display_imageView)
    ImageView displayImageView;

    String image, images;
    @BindView(R.id.image_back)
    ImageView imageBack;
    String phone;
    @BindView(R.id.product_details_call_button)
    Button productDetailsCallButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        ButterKnife.bind(this);

        Intent i = this.getIntent();
        image = i.getExtras().getString("imageId_key");

        if (image != null) {
            Picasso.get()
                    .load(image)
                    .into(displayImageView);
        }

        images = i.getExtras().getString("imageIds_key");
        phone = i.getExtras().getString("phoneIds_key");
        if(phone == null){

        }
        else{
            productDetailsCallButton.setVisibility(View.VISIBLE);
        }
        if (images != null) {
            Picasso.get()
                    .load(images)
                    .into(displayImageView);
        }

    }

//    @OnClick(R.id.image_back)
//    public void onViewClicked() {
//        onBackPressed();
//    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @OnClick({R.id.image_back, R.id.product_details_call_button})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                onBackPressed();
                break;
            case R.id.product_details_call_button:
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phone));
                startActivity(intent);
                break;
        }
    }

//    @OnClick(R.id.product_details_call_button)
//    public void onViewClicked() {
//        Intent intent = new Intent(Intent.ACTION_DIAL);
//        intent.setData(Uri.parse("tel:" + phone));
//        startActivity(intent);
//    }
}
