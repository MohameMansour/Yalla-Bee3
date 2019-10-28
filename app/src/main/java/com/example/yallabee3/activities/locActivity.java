package com.example.yallabee3.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yallabee3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class locActivity extends AppCompatActivity {

    @BindView(R.id.lat_editText5)
    TextView showLocationTxt;
    @BindView(R.id.lang_editText6)
    TextView langEditText;
    @BindView(R.id.button)
    Button getlocationBtn;

    private static  final int REQUEST_LOCATION=1;
    LocationManager locationManager;
    String latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loc);
        ButterKnife.bind(this);

        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {

        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check gps is enable or not

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            //Write Function To enable gps

            OnGPS();
        }
        else
        {
            //GPS is already On then

            getLocation();
        }
    }
//
private void getLocation() {

    //Check Permissions again

    if (ActivityCompat.checkSelfPermission(locActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(locActivity.this,

            Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
    {
        ActivityCompat.requestPermissions(this,new String[]
                {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
    }
    else
    {
        Location LocationGps= locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location LocationNetwork=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        Location LocationPassive=locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        if (LocationGps !=null)
        {
            double lat=LocationGps.getLatitude();
            double longi=LocationGps.getLongitude();

            latitude=String.valueOf(lat);
            longitude=String.valueOf(longi);

            showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
        }
        else if (LocationNetwork !=null)
        {
            double lat=LocationNetwork.getLatitude();
            double longi=LocationNetwork.getLongitude();

            latitude=String.valueOf(lat);
            longitude=String.valueOf(longi);

            showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
        }
        else if (LocationPassive !=null)
        {
            double lat=LocationPassive.getLatitude();
            double longi=LocationPassive.getLongitude();

            latitude=String.valueOf(lat);
            longitude=String.valueOf(longi);

            showLocationTxt.setText("Your Location:"+"\n"+"Latitude= "+latitude+"\n"+"Longitude= "+longitude);
        }
        else
        {
            Toast.makeText(this, "Can't Get Your Location", Toast.LENGTH_SHORT).show();
        }

        //Thats All Run Your App
    }

}

    private void OnGPS() {

        final AlertDialog.Builder builder= new AlertDialog.Builder(this);

        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        final AlertDialog alertDialog=builder.create();
        alertDialog.show();
    }

        }
