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
        //只第一次打开使用
        try {
            InputStream is = null;
            try {
                //解析city json文件
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
                        } else if (name.equals("cityEn")) {
                            CityEn = reader.nextString();
                        } else if (name.equals("provinceEn")) {
                            provinceEn = reader.nextString();
                        }else {
                            reader.skipValue();
                        }

                    }
                    //保存到数据库
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
        }
        return true;
    }



    public static Weather handleWeatherResponse(String response){
        try{
            //解析天气api json文件
            JSONObject jsonObject=new JSONObject(response);
            String weatherContent=jsonObject.toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }catch (Exception e){
            e.printStackTrace();
        }return null;
    }
}
