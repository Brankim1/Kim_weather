package com.example.kim_weather.gson;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Weather {

    @SerializedName("cityid")
    public  String cityid;

    @SerializedName("update_time")
    public  String updateTime;

    @SerializedName("city")
    public  String city;

    @SerializedName("country")
    public  String country;

    @SerializedName("data")
    public List<Forecast>forecastList;


}
