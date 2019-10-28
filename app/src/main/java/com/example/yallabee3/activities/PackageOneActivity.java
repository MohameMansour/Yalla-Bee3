package com.example.yallabee3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.yallabee3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PackageOneActivity extends AppCompatActivity {

    @BindView(R.id.btn_packageone)
    Button btnPackageone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_one);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_packageone)
    public void onViewClicked() {
        Intent i = new Intent(PackageOneActivity.this , PayActivity.class);
        startActivity(i);
    }
}
