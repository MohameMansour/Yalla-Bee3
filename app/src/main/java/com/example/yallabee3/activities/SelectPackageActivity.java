package com.example.yallabee3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.yallabee3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPackageActivity extends AppCompatActivity {


    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_two)
    Button btnTwo;
    @BindView(R.id.btn_three)
    Button btnThree;
    @BindView(R.id.button_skip)
    Button buttonSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_package);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_one, R.id.btn_two, R.id.btn_three})
    public void onViewClicked(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.btn_one:
                i = new Intent(SelectPackageActivity.this, PackageOneActivity.class);
                startActivity(i);
                break;
            case R.id.btn_two:
                i = new Intent(SelectPackageActivity.this, PackageTwoActivity.class);
                startActivity(i);
                break;
            case R.id.btn_three:
                break;
        }
    }

    @OnClick(R.id.button_skip)
    public void onViewClicked() {
        Intent x = new Intent(SelectPackageActivity.this, NavActivity.class);
        startActivity(x);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent x = new Intent(SelectPackageActivity.this, NavActivity.class);
        startActivity(x);
        finish();
    }

    //    @OnClick({R.id.imageView2, R.id.imageView3, R.id.imageView4})
//    public void onViewClicked(View view) {
//        Intent i;
//        switch (view.getId()) {
//            case R.id.imageView2:
//                i = new Intent(SelectPackageActivity.this , PackageOneActivity.class);
//                startActivity(i);
//                break;
//            case R.id.imageView3:
//                i = new Intent(SelectPackageActivity.this , PackageTwoActivity.class);
//                startActivity(i);
//                break;
//            case R.id.imageView4:
//                break;
//        }
//    }
}
