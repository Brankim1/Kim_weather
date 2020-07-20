package com.example.kim_weather.db;

import org.litepal.crud.LitePalSupport;

public class cityId extends LitePalSupport {

    private String cityid;
    private String cityEn;
    private String provinceEn;

    public String getId(){
        return cityid;
    }
    public void setId(String cityid){
        this.cityid=cityid;
    }

    public String getCityEn(){
        return cityEn;
    }
    public  void setCityEn(String cityEn){
        this.cityEn=cityEn;
    }

    public String getProvinceEn(){
        return provinceEn;
    }
    public  void setProvinceEn(String provinceEn){
        this.provinceEn=provinceEn;
    }



}
