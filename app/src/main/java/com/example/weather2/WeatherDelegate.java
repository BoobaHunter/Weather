package com.example.weather2;

/**
 * Делегат погоды
 */

public interface WeatherDelegate{
    //произошло обновление погоды
    void onUpdate();

    //Произошла ошибка
    void onError();
}