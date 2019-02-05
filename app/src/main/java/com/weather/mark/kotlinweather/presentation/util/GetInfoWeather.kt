package com.weather.mark.kotlinweather.presentation.util

import android.content.Context
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.ForecastDAO
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
            "03d", "03n", "04d", "04n" -> return R.drawable.clouds
            "09d", "09n" -> return R.drawable.shower_rain
            "10d", "10n" -> return R.drawable.rain
            "11d", "11n" -> return R.drawable.thunderstorm
            "13d", "13n" -> return R.drawable.snows
            "50d", "50n" -> return R.drawable.mist_d
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

    companion object {
        fun getBackgroundColor(indexCity: Int, context: Context): Int {
            val dao = ForecastDAO(context)
            val forecast = dao.getForecasts()[indexCity]
            val icon = forecast.list[0].weatherDescription[0].icon
            return getColor(icon, context)
        }

        private fun getColor(icon: String, context: Context): Int {
            val background = context.resources.getIntArray(R.array.backgroung_color)
            when (icon) {
                "01d" -> return background[0]
                "01n" -> return background[1]
                "02d", "02n" -> return background[2]
                "03d", "03n", "04d", "04n" -> return background[4]
                "09d", "09n", "10d", "10n" -> return background[5]
                "11d", "11n" -> return background[6]
                "13d", "13n" -> return background[7]
                "50d", "50n" -> return background[8]
            }
            return -1
        }
    }
}
