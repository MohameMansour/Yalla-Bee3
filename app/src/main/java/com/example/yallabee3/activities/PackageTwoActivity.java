package com.example.yallabee3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.yallabee3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PackageTwoActivity extends AppCompatActivity {
    @BindView(R.id.btn_packagetwo)
    Button btnPackagetwo;

//    @BindView(R.id.imageView2)
//    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_two);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_packagetwo)
    public void onViewClicked() {
        Intent i = new Intent(PackageTwoActivity.this , PayActivity.class);
        startActivity(i);
    }

//    @OnClick(R.id.imageView2)
//    public void onViewClicked() {
//        Intent i = new Intent(PackageTwoActivity.this , PayActivity.class);
//        startActivity(i);
//    }
}
