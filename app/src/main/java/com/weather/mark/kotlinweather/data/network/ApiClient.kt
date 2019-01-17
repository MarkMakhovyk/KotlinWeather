package com.weather.mark.kotlinweather.data.network

import android.app.Application
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class ApiClient : Application(){
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(1,TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .build()
    private var weatherApi: WeatherApi
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    init {
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherApi(): WeatherApi{
        return weatherApi
    }
}






