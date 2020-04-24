package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class SplashScreen extends AppCompatActivity implements View.OnClickListener , LocationListener {

    com.google.android.material.button.MaterialButton search,current;
    LocationManager locationManager;
    double longi,lat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        current = findViewById(R.id.current);
        search = findViewById(R.id.search);
        current.setOnClickListener(this);
        search.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.current:
                CheckLocation();
                break;
            case R.id.search:
                Intent i = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(i);
                break;
        }



    }

    private void CheckLocation() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if(!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
                    return ;
                }

                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


                return;
            }
        }

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        Intent i = new Intent(SplashScreen.this,AccueilActivity.class);
            i.putExtra("longi",longi);
            i.putExtra("lat",lat);
            startActivity(i);




//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                if(!shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION))
//                {
//                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
//                    return ;
//                }
//
//                ActivityCompat.requestPermissions(SplashScreen.this,
//                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//
//
//                return;
//            }
//        }
//
//        Location lastLocationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//        if (lastLocationGPS == null)
//        {
//            //location hethi class ely traja3lek logi w lati
//            lastLocationGPS = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//            double longi = lastLocationGPS.getLongitude();
//            double lat = lastLocationGPS.getLatitude();
//            Log.d("location_log:", String.valueOf(longi));
//            Log.d("location_lat:", String.valueOf(lat));
//            Toast.makeText(this, "lat : "+lat, Toast.LENGTH_SHORT).show();
////            Intent i = new Intent(SplashScreen.this,AccueilActivity.class);
////            i.putExtra("longi",longi);
////            i.putExtra("lat",lat);
////            startActivity(i);
//
//
//        }else
//            Toast.makeText(SplashScreen.this, "Trouble in location "
//                    , Toast.LENGTH_SHORT).show();
//
//


    }

    @Override
    public void onLocationChanged(Location location) {
         longi = location.getLongitude();
          lat = location.getLatitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

}
