package com.weather.mark.kotlinweather.data.network

import com.weather.mark.kotlinweather.data.model.Forecast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetForecast(private val callbackLoadWeather: CallbackLoadForecast) : Callback<Forecast> {
    private var service = ApiClient()

    fun getByCity(cityName: String) {
        service.getWeatherApi().getForecastByCity(
            cityName,
            WeatherApi.Const.LAND,
            WeatherApi.Const.API_KEY
        ).enqueue(this)
    }

    fun getByLocation(lat: String, lon: String) {

        service.getWeatherApi().getForecastByLocation(
            lat,
            lon,
            WeatherApi.Const.LAND,
            WeatherApi.Const.API_KEY
        ).enqueue(this)
    }


    override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
        if (response != null)
            callbackLoadWeather.onResponse(response.body())
    }

    override fun onFailure(call: Call<Forecast>, t: Throwable) {

    }

    interface CallbackLoadForecast {
        fun onResponse(forecast: Forecast?)
    }

}