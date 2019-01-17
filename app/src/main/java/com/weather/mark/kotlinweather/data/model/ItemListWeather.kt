package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ItemListWeather(
    @SerializedName("dt")
    @Expose
    var dt: Int,
    @SerializedName("main")
@Expose
    var main: Main,
@SerializedName("clouds")
@Expose
    var clouds: Clouds,
@SerializedName("wind")
@Expose
    var wind: Wind,

@SerializedName("dt_txt")
@Expose
    var dtTxt: String,
@SerializedName("weather")
@Expose
    var weatherDescription: List<WeatherDescription>
)