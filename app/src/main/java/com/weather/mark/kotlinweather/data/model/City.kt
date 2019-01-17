package com.weather.mark.kotlinweather.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class City (
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String
)
