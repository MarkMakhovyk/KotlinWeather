package com.weather.mark.kotlinweather.data.bd

class WeatherDbSchema {
    object WeatherTable {
        const val NAME = "weather"

        object Cols {
            const val CITY_NAME = "city_name"
            const val JSON = "json"
        }
    }
}