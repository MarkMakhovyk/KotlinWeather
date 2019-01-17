package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Main (
    @SerializedName("temp")
    @Expose
    var temp: Double,
    @SerializedName("pressure")
    @Expose
    var pressure: Double,
    @SerializedName("humidity")
    @Expose
    var humidity: Int
    )
