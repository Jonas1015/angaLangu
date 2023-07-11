package com.nocturnalcodes.angalangu;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private CurrentWeather currentWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView openMeteo = findViewById(R.id.openMeteoAttribution);
        openMeteo.setMovementMethod(LinkMovementMethod.getInstance());

        String X_RapidAPI_Key = "5c4393051qfmsha314ba1f01a8bf7p1a8dd9jsn2148f5e3255e";
        String X_RapidAPI_Host = "dark-sky.p.rapidapi.com";
        double latitude =  37.8267;
        double longitude =  -122.4233;
//        String forecastURL = "https://dark-sky.p.rapidapi.com/{" + latitude + "},{" + longitude + "}";
        String forecastURL = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude + "&longitude=" + longitude + "&current_weather=true&timezone=Africa/Dar_es_Salaam&hourly=temperature_2m,relativehumidity_2m,windspeed_10m&timeformat=unixtime";
        if(isNetworkAvailable()){
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(forecastURL)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        if(response.isSuccessful()){
                            String jsonData = response.body().string();

                            currentWeather = getCurrentDetails(jsonData);
                        }
                        else {
                            Log.e(TAG, response.body().string());
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Can't get the response! Exception: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON exception caught: ", e);
                    }
                }
            });
        }

    }

    private CurrentWeather getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast = new JSONObject(jsonData);
        JSONObject current_weather = forecast.getJSONObject("current_weather");

        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setTemperature(current_weather.getDouble("temperature"));
        currentWeather.setWeathercode(current_weather.getDouble("weathercode"));
        currentWeather.setTime(current_weather.getInt("time"));
        currentWeather.setIs_day(current_weather.getInt("is_day"));
        currentWeather.setWinddirection(current_weather.getDouble("winddirection"));
        currentWeather.setWindspeed(current_weather.getDouble("windspeed"));
        currentWeather.setTimezone(forecast.getString("timezone"));

        Log.d(TAG, "time got:" + currentWeather.getFormtedTime());
        return currentWeather;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkinfo != null && networkinfo.isConnected()){
            isAvailable = true;
        }
        else {
            Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }

        return isAvailable;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog = new AlertDialogFragment();
        dialog.show(getFragmentManager(), "error_dialog");
    }
}