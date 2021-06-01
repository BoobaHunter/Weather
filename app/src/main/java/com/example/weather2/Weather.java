package com.example.weather2;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class Weather {

    //hjlbntkm
    private Context mParent;
    //город
    private String mCity;
    //ключ приложения
    private String mAPIKey;
    //язык
    private String mLang;
    //очередь запросов
    private RequestQueue mRequestQueue;
    //Время обновления
    private int mUpdateTime = 0;
    //таймер обновления
    private Timer mTimer = null;
    //делегат погоды
    private WeatherDelegate mDelegate = null;
    //текущая температура
    int currentTemperature = 0;
    //иконка погоды
    String icon;
    //Ощущается как
    int feelsLike=0;
    //минимальная темперптура
    int minTemp =0;
    //максимальная температура
    int maxTemp =0;
    //давление
    int pressure = 0;
    //влажность
    int humidity = 0;
    //описание
    String description;
    //видимость
    int visibility =0;
    //Скорость ветра
    int windSpeed = 0;
    //направление ветра
    int windDeg = 0;
    //облака
    int clouds =0;
    //закат
    long sunSer = 0;
    //рассыет
    long sunRise =0;


    //Протокол
    private final String PROTOCOL="https://";

    //Хост сервиса
    private final String HOST="api.openweathermap.org";
    private final String HOST_IMAGE = "openweathermap.org";

    //путь запроса
    private final String REQUEST_PATH_CURRENT ="/data/2.5/weather";



    Weather(Context parent, String city, String apiKey, String lang, int updateTime)
    {
        mParent = parent;
        mCity = city;
        mAPIKey = apiKey;
        mLang = lang;
        mUpdateTime = updateTime;

        init();
    }


    void update()
    {

        String url = PROTOCOL
                +HOST
                +REQUEST_PATH_CURRENT
                +"?q="+mCity
                +"&"
                +"appid="+mAPIKey
                +"&"
                +"lang="+mLang;

        send(url);


    }

    private void init()
    {

        mRequestQueue = Volley.newRequestQueue(mParent);

        if(mUpdateTime !=0)
        {
            mTimer = new Timer();
            mTimer.scheduleAtFixedRate(new TimerTask(){
                @Override
                public void run()
                {

                    update();
                }
            },0,mUpdateTime);
        }
    }


    private void parser(JSONObject response)
    {
        try{

            JSONObject main = response.getJSONObject("main");
            currentTemperature = kelvinToCel(main.getDouble("temp"));
            feelsLike=kelvinToCel(main.getDouble("feels_like"));
            minTemp = kelvinToCel(main.getDouble("temp_min"));
            maxTemp = kelvinToCel(main.getDouble("temp_max"));
            pressure = (int) main.getDouble("pressure");
            humidity = (int) main.getDouble("humidity");

            visibility = (int) response.getDouble("visibility");
            JSONObject wind = response.getJSONObject("wind");
            windSpeed = (int)wind.getDouble("speed");
            windDeg = (int) wind.getDouble("deg");
            JSONObject jsonclouds = response.getJSONObject("clouds");
            clouds= (int) jsonclouds.getDouble("all");

            JSONObject jsonsys = response.getJSONObject("sys");
            sunSer = (long) jsonsys.getDouble("sunset");
            sunRise = (long) jsonsys.getDouble("sunrise");
















            JSONArray weather = response.getJSONArray("weather");
            JSONObject current = weather.getJSONObject(0);
            icon = prepareWeatherIconURI(current.getString("icon"));





            if(mDelegate != null)
                mDelegate.onUpdate();
        }
        catch (JSONException e){

            e.printStackTrace();

            onError();
        }
    }


    private String prepareWeatherIconURI(String icon)
    {
        return PROTOCOL + HOST_IMAGE + "/img/wn"+icon+"@4x.png";
    }


    private int kelvinToCel(double value)
    {
        return (int)Math.round(value - 273.15);
    }



    private void onError()
    {

        if(mDelegate != null)
            mDelegate.onError();


        if(mTimer !=null)
            mTimer.cancel();
    }


    private void send(String url)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                parser(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                onError();
            }
        });


        mRequestQueue.add(request);
    }


    void setCity(String city)
    {
        if (!city.isEmpty()){
            mCity=city;

            update();
        }
    }


    void setDelegate(WeatherDelegate delegate)
    {
        mDelegate = delegate;
    }

}


