package com.weather.mark.kotlinweather.presentation.util

import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.model.Forecast
import java.text.SimpleDateFormat
import java.util.*

class GetInfoWeather internal constructor(forecast: Forecast) {
    private val TAG = "Forecast"
    var forecast: Forecast = forecast

    fun getDay(index: Int): String {
        return SimpleDateFormat("EEE", Locale("ru"))
            .format(Date(forecast.list.get(index).dt * 1000L))
    }

    fun getTime(index: Int): String {
        return SimpleDateFormat("HH", Locale("ru"))
            .format(Date(forecast.list.get(index).dt * 1000L))
    }

    fun getIcon(index: Int): Int {
        val icon = forecast.list.get(index).weatherDescription.get(0).icon
        when (icon) {
            "01d" -> return R.drawable.clear_sky_d
            "01n" -> return R.drawable.clear_sky_n
            "02d" -> return R.drawable.few_clouds_d
            "02n" -> return R.drawable.few_clouds_n
            "03d" -> return R.drawable.clouds
            "03n" -> return R.drawable.clouds
            "04d" -> return R.drawable.clouds
            "04n" -> return R.drawable.clouds
            "09d" -> return R.drawable.shower_rain
            "09n" -> return R.drawable.shower_rain
            "10d" -> return R.drawable.rain
            "10n" -> return R.drawable.rain
            "11d" -> return R.drawable.thunderstorm
            "11n" -> return R.drawable.thunderstorm
            "13d" -> return R.drawable.snows
            "13n" -> return R.drawable.snows
            "50d" -> return R.drawable.mist_d
            "50n" -> return R.drawable.mist_n
        }
        return -1
    }

    fun getDescription(index: Int): String {
        return forecast.list.get(index).weatherDescription.get(0).description
    }

    fun getTemp(index: Int): String {
        return ((forecast.list.get(index).main.temp.toInt() - 273)).toString() + "\u00B0"
    }

    fun getWeatherHumidity(index: Int): String {
        return forecast.list.get(index).main.humidity.toString() + "%"
    }

    fun getWeatherPressure(index: Int): String {
        var pressure = forecast.list.get(index).main.pressure
        pressure *= 0.750062

        return pressure.toInt().toString() + "мм"
    }

    fun getWeatherWind(index: Int): String {
        val speed = forecast.list.get(index).wind.speed
        return speed.toInt().toString() + "м/с"
    }

    fun getWeatherClouds(index: Int): String {
        val clouds = forecast.list.get(index).clouds.all
        return clouds.toString() + "%"
    }
}
