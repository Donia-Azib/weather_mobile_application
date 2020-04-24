package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    com.google.android.material.button.MaterialButton btn,more;
    TextView txt;
    EditText edit;
    RelativeLayout relative;
    ImageView img;
    double longi,lat;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        more = findViewById(R.id.more);
        txt = findViewById(R.id.afficher);
        relative = findViewById(R.id.relative);
        img = findViewById(R.id.icon);

        String apiKey = getString(R.string.api_key);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);}

        PlacesClient placesClient = Places.createClient(this);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                name= place.getName();
                Log.d("location", "Place: " + place.getName() + ", " + place.getId());
                GetWeather(name);
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("Error", "An error occurred: " + status);
            }
        });


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,AccueilActivity.class);
                i.putExtra("name",name);
                startActivity(i);
                finish();
            }
        });




    }




    private void GetWeather(String pays) {
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+pays+"&appid=635fbfa58dfddd0c56b8b94cdd08517a";


        Ion.with(MainActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null)
                        {
                            Toast.makeText(MainActivity.this, "Error !"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(result != null )
                            {
                                try {

                                    JsonObject coord = result.get("coord").getAsJsonObject();
                                    longi = coord.get("lon").getAsDouble();
                                    lat = coord.get("lat").getAsDouble();

                                    JsonObject weather = result.get("weather").getAsJsonArray().get(0).getAsJsonObject();
                                    String weatherDescription = weather.get("description").getAsString();
                                    String icon = weather.get("icon").getAsString();
                                    Toast.makeText(MainActivity.this, ""+icon, Toast.LENGTH_SHORT).show();
                                        txt.setText("Weather "+weatherDescription);
                                        Picasso.get().load("http://openweathermap.org/img/w/"+icon+".png")
                                                .resize(200, 200)
                                                .centerCrop()
                                                .into(img);

                                        //edit.setText("");
                                        more.setVisibility(View.VISIBLE);




                                }catch(NoClassDefFoundError noClassDefFoundError)
                                {
                                    Toast.makeText(MainActivity.this, "check the place"+noClassDefFoundError.getMessage(), Toast.LENGTH_LONG).show();
                                }
                                catch(Exception ex)
                                {
                                    Toast.makeText(MainActivity.this, "Check the place "+ex.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            }
                            else
                                txt.setText("u have another chance to change ");


                        }
                    }
                });
    }


}
