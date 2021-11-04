package com.example.weather.service

import com.example.weather.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.openweathermap.org/data/2.5/weather?q=Izmir&APPID=0ac4876f3ffa47be36009208652e72e5
interface WeatherAPI {
    @GET("data/2.5/weather?&units=metric&APPID=0ac4876f3ffa47be36009208652e72e5")
    fun getData(
        @Query("q") cityName: String
    ):Single<WeatherModel>
}