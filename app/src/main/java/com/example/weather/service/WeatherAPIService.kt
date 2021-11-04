package com.example.weather.service

import com.example.weather.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//https://api.openweathermap.org/data/2.5/weather?q=Izmir&APPID=0ac4876f3ffa47be36009208652e72e5
class WeatherAPIService {
    private val BASE_URL = "https://api.openweathermap.org/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(WeatherAPI::class.java)

    fun getDataServise(cityName: String): Single<WeatherModel>{
        return api.getData(cityName)
    }
}