package com.nocturnalcodes.angalangu;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CurrentWeather {
    private double temperature;
    private double windspeed;
    private double winddirection;
    private double weathercode;
    private int is_day;
    private String timezone;
    private int time;

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }


    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getWindspeed() {
        return windspeed;
    }

    public void setWindspeed(double windspeed) {
        this.windspeed = windspeed;
    }

    public double getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(double winddirection) {
        this.winddirection = winddirection;
    }

    public double getWeathercode() {
        return weathercode;
    }

    public void setWeathercode(double weathercode) {
        this.weathercode = weathercode;
    }

    public int getIs_day() {
        return is_day;
    }

    public void setIs_day(int is_day) {
        this.is_day = is_day;
    }

    public int getTime() {
        return time;
    }

    public String getFormtedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");

        formatter.setTimeZone(TimeZone.getTimeZone(timezone));

        Date dateTime = new Date(time*1000);
        return formatter.format(dateTime);
    }

    public void setTime(int time) {
        this.time = time;
    }
}
