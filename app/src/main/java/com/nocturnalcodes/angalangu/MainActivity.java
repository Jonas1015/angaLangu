package com.nocturnalcodes.angalangu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.nocturnalcodes.angalangu.databinding.ActivityMainBinding;

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
    private ImageView iconImageView;
    final double latitude =  37.8267;
    final double longitude =  -122.4233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getForecast(latitude, longitude);
    }

    private void getForecast(double latitude, double longitude) {
        final ActivityMainBinding binding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);

        TextView openMeteo = findViewById(R.id.openMeteoAttribution);
        openMeteo.setMovementMethod(LinkMovementMethod.getInstance());

        iconImageView = findViewById(R.id.iconImageView);

        String X_RapidAPI_Key = "5c4393051qfmsha314ba1f01a8bf7p1a8dd9jsn2148f5e3255e";
        String X_RapidAPI_Host = "dark-sky.p.rapidapi.com";
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

                            final CurrentWeather displayWeather = new CurrentWeather(
                                    currentWeather.getTemperature(),
                                    currentWeather.getWindspeed(),
                                    currentWeather.getWinddirection(),
                                    currentWeather.getWeathercode(),
                                    currentWeather.getIs_day(),
                                    currentWeather.getTimezone(),
                                    currentWeather.getTime()
                            );

                            binding.setWeather(displayWeather);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Drawable drawable = getResources().getDrawable(displayWeather.getIconId());
                                    iconImageView.setImageDrawable(drawable);
                                }
                            });
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

    public void refreshOnClick(View view){
        Toast.makeText(this, "Refreshing data", Toast.LENGTH_LONG).show();
        getForecast(latitude, longitude);
    }
}