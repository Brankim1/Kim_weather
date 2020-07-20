package com.example.kim_weather.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import com.example.kim_weather.db.cityId;
import com.example.kim_weather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static org.litepal.LitePalApplication.getContext;

public class Utility {
    public static boolean saveCity() {
        try {
            InputStream is = null;
            try {

                is = getContext().getAssets().open("city.json");
                JsonReader reader = new JsonReader(new InputStreamReader(is));
                reader.beginArray();
                while (reader.hasNext()) {

                    reader.beginObject();
                    String id = "";
                    String CityEn = "";
                    String provinceEn = "";
                    while (reader.hasNext()) {
                        String name = reader.nextName();
                        if (name.equals("id")) {
                            id = reader.nextString();
                        } else if (name.equals("cityEn")) { // 当前获取的字段是否为：null
                            CityEn = reader.nextString();
                        } else if (name.equals("provinceEn")) { // 当前获取的字段是否为：null
                            provinceEn = reader.nextString();
                        }else {
                            reader.skipValue();
                        }

                    }
                    cityId cityId1 = new cityId();
                    cityId1.setId(id);
                    cityId1.setCityEn(CityEn);
                    cityId1.setProvinceEn(provinceEn);
                    cityId1.save();

                    reader.endObject();
                }
                reader.endArray();
            } finally {
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }Log.d("第一个测试","第一个测试"+LitePal.findFirst(cityId.class).getCityEn());
        return true;
    }



    public static Weather handleWeatherResponse(String response){
        try{
            JSONObject jsonObject=new JSONObject(response);
            String weatherContent=jsonObject.toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }return null;
    }
}
