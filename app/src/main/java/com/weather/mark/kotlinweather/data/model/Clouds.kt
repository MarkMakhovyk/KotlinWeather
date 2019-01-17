package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Clouds (
    @SerializedName("all")
    @Expose
    var all: Int
)
