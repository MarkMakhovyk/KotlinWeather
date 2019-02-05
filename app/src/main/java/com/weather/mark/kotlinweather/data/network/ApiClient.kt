package com.weather.mark.kotlinweather.data.network

import android.app.Application
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient : Application(){
    private lateinit var okHttpClient: OkHttpClient
    private var weatherApi: WeatherApi
    private lateinit var retrofit: Retrofit

    private fun createApi() {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient.Builder()
//            .connectTimeout(1, TimeUnit.SECONDS)
//            .readTimeout(1, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    init {
        createApi()
        weatherApi = retrofit.create(WeatherApi::class.java)
    }

    fun getWeatherApi(): WeatherApi{
        return weatherApi
    }
}






