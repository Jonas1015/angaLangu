package com.nocturnalcodes.angalangu;

public class CurrentWeather {
    private double temperature;
    private double windspeed;
    private double winddirection;
    private double weathercode;
    private int is_day;
    private int time;

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

    public String getTime() {
        return time;
    }

    public void setTime(Int time) {
        this.time = time;
    }
}
