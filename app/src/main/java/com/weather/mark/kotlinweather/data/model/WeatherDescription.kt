package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherDescription (
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("main")
    @Expose
    var main: String,
    @SerializedName("description_location")
    @Expose
    var description: String,
    @SerializedName("icon")
    @Expose
    var icon: String
)
