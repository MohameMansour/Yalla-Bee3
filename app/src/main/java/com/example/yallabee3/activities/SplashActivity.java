package com.example.yallabee3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.yallabee3.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    startActivity(new Intent(SplashActivity.this, NavActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, SignInActivity.class));
//                    startActivity(new Intent(SplashActivity.this , NavActivity.class));
                    finish();
                }
            }
        }, SPLASH_TIME);

    }
}
