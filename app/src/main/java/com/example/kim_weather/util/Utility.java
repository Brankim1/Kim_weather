package com.example.kim_weather.util;

import android.text.TextUtils;
import android.util.Log;

import com.example.kim_weather.db.City;
import com.example.kim_weather.db.County;
import com.example.kim_weather.db.Province;
import com.example.kim_weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class Utility {

    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try{
                JSONArray allProvinces=new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province=new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
                } catch (Exception e) {
                e.printStackTrace();
            }
        }return false;
    }

    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try{

                if (response.startsWith("\ufeff")) {
                    response = response.substring(1);
                }
                JSONArray allCities =new JSONArray(response);
                for(int i=0;i<allCities.length();i++) {
                    JSONObject provinceObject = allCities.getJSONObject(i);
                   City city=new City();
                    city.setCityName(provinceObject.getString("name"));
                    city.setCityCode(provinceObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }return false;
    }

    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try{Log.d("AAA","response is " +response);
                JSONArray allCounties=new JSONArray(response);
                for(int i=0;i<allCounties.length();i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county=new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }return false;
    }
    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            JSONArray jsonArray=jsonObject.getJSONArray("HeWeather");
            String weatherContent=jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }return null;
    }
}
