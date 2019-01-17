package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("cod")
    @Expose
    val cod: String,
    @SerializedName("cnt")
    @Expose
    private val cnt: Int,
    @SerializedName("list")
    @Expose var list: List<ItemListWeather>,
    @SerializedName("city")
    @Expose var city: City
)