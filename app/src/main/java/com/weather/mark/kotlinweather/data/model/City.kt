package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class City (
    @SerializedName("name")
    @Expose
    var name: String,
    var lat: String,
    var lon: String,
    var regions: String


)
