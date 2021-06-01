package com.example.weather2;

import android.app.Application;

import java.util.ArrayList;

public class MyApplication extends Application {


    private ArrayList<String> mCityManager;


    private Weather mWeather;


    public ArrayList<String>getCityManager()
    {return mCityManager;}

    public Weather getWeather()
    {return mWeather;}







    @Override
    public void onCreate(){
        super.onCreate();

        mCityManager = new ArrayList<String>();


        mCityManager.add("Нижний Тагил");


        mWeather = new Weather(this,
                mCityManager.get(0),
                "5a34c4912f7b8762c0cd50df129478a3",
                "ru",
                50000);
    }

}
