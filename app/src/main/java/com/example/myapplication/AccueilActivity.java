package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AccueilActivity extends AppCompatActivity {

    TextView addressTxt, updated_atTxt, statusTxt, tempTxt, temp_minTxt, temp_maxTxt, sunriseTxt,
            sunsetTxt, windTxt, pressureTxt, humidityTxt;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        double lat  = intent.getDoubleExtra("lat",0);
        double longi  = intent.getDoubleExtra("longi",0);

        addressTxt = findViewById(R.id.address);
        updated_atTxt = findViewById(R.id.updated_at);
        statusTxt = findViewById(R.id.status);
        tempTxt = findViewById(R.id.temp);
        temp_minTxt = findViewById(R.id.temp_min);
        temp_maxTxt = findViewById(R.id.temp_max);
        sunriseTxt = findViewById(R.id.sunrise);
        sunsetTxt = findViewById(R.id.sunset);
        windTxt = findViewById(R.id.wind);
        pressureTxt = findViewById(R.id.pressure);
        humidityTxt = findViewById(R.id.humidity);


        if (name != null)
            GetLocalWeatherName(name);
        else
            GetLocationWeather(longi,lat);





    }

    private void GetLocalWeatherName(String name) {
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+name+"&units=metric&appid=635fbfa58dfddd0c56b8b94cdd08517a";


        Ion.with(AccueilActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null)
                        {
                            Toast.makeText(AccueilActivity.this, "Error !"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(result != null )
                            {
                                try {
                                    JsonObject main = result.get("main").getAsJsonObject();
                                    JsonObject sys = result.get("sys").getAsJsonObject();
                                    JsonObject wind = result.get("wind").getAsJsonObject();
                                    JsonObject weather = result.get("weather").getAsJsonArray().get(0).getAsJsonObject();


                                    Long updatedAt = result.get("dt").getAsLong();
                                    String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                                    String temp = main.get("temp").getAsString()+ "°C";
                                    String tempMin = "Min Temp: " + main.get("temp_min").getAsString()+ "°C";
                                    String tempMax = "Max Temp: " + main.get("temp_max").getAsString()+ "°C";
                                    String pressure = main.get("pressure").getAsString();
                                    String humidity = main.get("humidity").getAsString();

                                    Long sunrise = result.get("sys").getAsJsonObject().get("sunrise").getAsLong();
                                    Long sunset = result.get("sys").getAsJsonObject().get("sunset").getAsLong();
                                    String windSpeed = wind.get("speed").getAsString();
                                    String weatherDescription = weather.get("description").getAsString();

                                    String address = result.get("name").getAsString() + ", " + sys.get("country").getAsString();


                                    /* Populating extracted data into our views */
                                    addressTxt.setText(address);
                                    updated_atTxt.setText(updatedAtText);
                                    statusTxt.setText(weatherDescription.toUpperCase());
                                    tempTxt.setText(temp);
                                    temp_minTxt.setText(tempMin);
                                    temp_maxTxt.setText(tempMax);
                                    sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                                    sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                                    windTxt.setText(windSpeed);
                                    pressureTxt.setText(pressure);
                                    humidityTxt.setText(humidity);


                                }catch(Exception ex)
                                {
                                    Toast.makeText(AccueilActivity.this, "Exception "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            else
                            {
                                Toast.makeText(AccueilActivity.this, "You have the time to change", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });
    }

    private void GetLocationWeather(double longi, double lat) {

        String url ="https://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+longi+"&units=metric&appid=635fbfa58dfddd0c56b8b94cdd08517a";


        Ion.with(AccueilActivity.this)
                .load(url)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        if(e != null)
                        {
                            Toast.makeText(AccueilActivity.this, "Error !"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            if(result != null )
                            {
                                try {
                                    JsonObject main = result.get("main").getAsJsonObject();
                                    JsonObject sys = result.get("sys").getAsJsonObject();
                                    JsonObject wind = result.get("wind").getAsJsonObject();
                                    JsonObject weather = result.get("weather").getAsJsonArray().get(0).getAsJsonObject();


                                    Long updatedAt = result.get("dt").getAsLong();
                                    String updatedAtText = "Updated at: " + new SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(new Date(updatedAt * 1000));
                                    String temp = main.get("temp").getAsString()+ "°C";
                                    String tempMin = "Min Temp: " + main.get("temp_min").getAsString()+ "°C";
                                    String tempMax = "Max Temp: " + main.get("temp_max").getAsString()+ "°C";
                                    String pressure = main.get("pressure").getAsString();
                                    String humidity = main.get("humidity").getAsString();

                                    Long sunrise = result.get("sys").getAsJsonObject().get("sunrise").getAsLong();
                                    Long sunset = result.get("sys").getAsJsonObject().get("sunset").getAsLong();
                                    String windSpeed = wind.get("speed").getAsString();
                                    String weatherDescription = weather.get("description").getAsString();

                                    String address = result.get("name").getAsString() + ", " + sys.get("country").getAsString();


                                    /* Populating extracted data into our views */
                                    addressTxt.setText(address);
                                    updated_atTxt.setText(updatedAtText);
                                    statusTxt.setText(weatherDescription.toUpperCase());
                                    tempTxt.setText(temp);
                                    temp_minTxt.setText(tempMin);
                                    temp_maxTxt.setText(tempMax);
                                    sunriseTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunrise * 1000)));
                                    sunsetTxt.setText(new SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(new Date(sunset * 1000)));
                                    windTxt.setText(windSpeed);
                                    pressureTxt.setText(pressure);
                                    humidityTxt.setText(humidity);


                                }catch(Exception ex)
                                {
                                    Toast.makeText(AccueilActivity.this, "Exception "+ex.getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                            else
                            {
                                Toast.makeText(AccueilActivity.this, "You have the time to change", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

    }
}
