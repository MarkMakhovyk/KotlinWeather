package com.weather.mark.kotlinweather.data

import com.google.gson.GsonBuilder
import com.weather.mark.kotlinweather.data.model.Forecast

object Json {
    fun jsonToWeather(json: String): Forecast? {
        val gson = GsonBuilder().create()
        var forecast: Forecast? = null
        try {
            forecast = gson.fromJson<Forecast>(json, Forecast::class.java!!)
        } catch (ioe: Exception) {
            ioe.printStackTrace()
        }

        return forecast
    }
}