package com.nocturnalcodes.angalangu;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String apiKey = "5c4393051fmsha314ba1f01a8bf7p1a8dd9jsn2148f5e3255e";
        double latitude = 37.8267;
        double longitude =  -122.4233;
        String forecastURL = "https://dark-sky.p.rapidapi.com/{" + latitude + "},{" + longitude + "}";

    }
}