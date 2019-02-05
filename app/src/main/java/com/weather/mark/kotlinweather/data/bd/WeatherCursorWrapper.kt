package com.weather.mark.kotlinweather.data.bd

import android.database.Cursor
import android.database.CursorWrapper
import com.weather.mark.kotlinweather.data.Json
import com.weather.mark.kotlinweather.data.model.Forecast


class WeatherCursorWrapper(cursor: Cursor) : CursorWrapper(cursor) {


    fun getWeather(): Forecast {
        val json = getString(getColumnIndex(WeatherDbSchema.WeatherTable.Cols.JSON))
        return Json.jsonToWeather(json)!!
    }
}