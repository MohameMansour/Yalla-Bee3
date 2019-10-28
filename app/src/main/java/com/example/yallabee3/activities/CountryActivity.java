package com.example.yallabee3.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.example.yallabee3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CountryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @BindView(R.id.spinner)
    AppCompatSpinner spinner;
    @BindView(R.id.enter_btn)
    Button enterBtn;
    Intent i ;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        ButterKnife.bind(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter
                .createFromResource(this, R.array.country3
                        , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setPrompt("Select Your Country");

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//        Intent i = new Intent(CountryActivity.this, SignUpActivity.class);
//        startActivity(i);
        text = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, "" + text, Toast.LENGTH_SHORT).show();
        if (position == 0) {
            i = new Intent(CountryActivity.this, SignUpActivity.class);

            i.putExtra("countryId_key", text);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        }
        if (position == 1) {
            i = new Intent(CountryActivity.this, SignUpEgyptActivity.class);

            i.putExtra("countryId_key", text);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("Current_Country", text);
//            editor.apply();

//            startActivity(i);
        }
        if (position == 2) {
            i = new Intent(CountryActivity.this, SignUpSodiaActivity.class);

            i.putExtra("countryId_key", text);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("Current_Country", text);
//            editor.apply();

//            startActivity(i);
        }
        if (position == 3) {
            i = new Intent(CountryActivity.this, SignUpEmaratActivity.class);

            i.putExtra("countryId_key", text);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

//            SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
//            SharedPreferences.Editor editor = sp.edit();
//            editor.putString("Current_Country", text);
//            editor.apply();

//            startActivity(i);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @OnClick(R.id.enter_btn)
    public void onViewClicked() {
        SharedPreferences sp = getSharedPreferences("SP_USER", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Current_Country", text);
        editor.apply();
        startActivity(i);
    }
}
