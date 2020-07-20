package com.example.kim_weather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.example.kim_weather.db.cityId;
import com.example.kim_weather.util.Utility;

import org.litepal.LitePal;

import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一次打开应用加载城市数据库
        SharedPreferences pre = getSharedPreferences("name",MODE_PRIVATE);
        boolean flag = pre.getBoolean("flag", false);

        if(flag == false){
            Utility.saveCity();
            pre.edit().putBoolean("flag", true).commit();
        }

        SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getString("weather",null)!=null){
            Intent intent=new Intent(this,WeatherActivity.class);
            startActivity(intent);
            finish();
        }
    }
}