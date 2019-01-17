package com.weather.mark.kotlinweather.data.network

import com.weather.mark.kotlinweather.data.model.Forecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi  {
    object Const {
        val LAND = "ru"
        val API_KEY = "2842e4fe031b0d2c1fa0294b481ff31a"
    }

    @GET("forecast")
    fun getData(@Query("q") query: String, @Query("lang") land: String, @Query("appid") APIKey: String)
            : Call<Forecast>
}

