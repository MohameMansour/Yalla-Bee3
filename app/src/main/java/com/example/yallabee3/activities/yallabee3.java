package com.example.yallabee3.activities;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class yallabee3 extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

//        Picasso.Builder builder = new Picasso.Builder(this);
//        builder.downloader(new OkHttp3Downloader(this ,Integer.MAX_VALUE));
//        Picasso built = builder.build();
//        built.setIndicatorsEnabled(false);
//        built.setLoggingEnabled(true);
//        Picasso.setSingletonInstance(built);
    }
}
