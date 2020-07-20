package com.example.kim_weather.gson;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("date")
    public String date;


    @SerializedName("tem1")
    public String max;
    @SerializedName("tem2")
    public String min;
    @SerializedName("tem")
    public String now;

    @SerializedName("wea")
    public String weather;

    @SerializedName("air")
    public String aqi;
}
