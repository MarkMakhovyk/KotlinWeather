package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed")
    @Expose
    var speed: Double,
    @SerializedName("deg")
    @Expose
    var deg: Double
)
