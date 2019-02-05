package com.weather.mark.kotlinweather.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.google.gson.Gson
import com.weather.mark.kotlinweather.data.bd.WeatherBaseHelper
import com.weather.mark.kotlinweather.data.bd.WeatherCursorWrapper
import com.weather.mark.kotlinweather.data.bd.WeatherDbSchema.WeatherTable
import com.weather.mark.kotlinweather.data.bd.WeatherDbSchema.WeatherTable.Cols.CITY_NAME
import com.weather.mark.kotlinweather.data.bd.WeatherDbSchema.WeatherTable.Cols.JSON
import com.weather.mark.kotlinweather.data.model.Forecast
import java.util.*

class ForecastDAO(context: Context) {


    private var context: Context
    private var mDatabase: SQLiteDatabase

    init {
        this.context = context.applicationContext
        mDatabase = WeatherBaseHelper(this.context).getWritableDatabase()
    }


    private fun addForecast(cityName: String, json: String) {
        val values = getContentValues(cityName, json)
        mDatabase.insert(WeatherTable.NAME, null, values)
    }

    private fun updateForecast(cityName: String, json: String) {

        val values = getContentValues(cityName, json)
        mDatabase.update(
            WeatherTable.NAME, values,
            "city_name = ?",
            arrayOf(cityName)
        )
    }

    fun getForecasts(): ArrayList<Forecast> {
        val forecasts = ArrayList<Forecast>()
        val cursor = queryForecast(null, null)
        try {
            cursor.moveToFirst()
            while (!cursor.isAfterLast()) {
                forecasts.add(cursor.getWeather())
                cursor.moveToNext()
            }
        } finally {
            cursor.close()
        }
        return forecasts
    }

    fun getForecast(cityName: String): Forecast? {

        val cursor = queryForecast(
            WeatherTable.Cols.CITY_NAME + " = ?",
            arrayOf(cityName)
        )

        try {
            if (cursor.count === 0) {
                return null
            }
            cursor.moveToFirst()
            return cursor.getWeather()
        } finally {
            cursor.close()
        }
    }


    fun addOrUpdate(forecast: Forecast?) {
        var isUpdate = false

        if (forecast == null) {
            return
        }
        val list = getForecasts()
        for (i in list.indices) {
            if (forecast!!.city.name == list[i].city.name) {
                updateForecast(forecast.city.name, forecastToJson(forecast))
                isUpdate = true
            }
        }
        if (!isUpdate) {
            addForecast(
                forecast!!.city.name,
                forecastToJson(forecast)
            )
        }
    }

    fun deleteForecast(cityName: String) {
        mDatabase.delete(WeatherTable.NAME, CITY_NAME + " = ?", arrayOf(cityName))
    }


    fun forecastToJson(forecast: Forecast): String {
        val gson = Gson()
        return gson.toJson(forecast)
    }

    private fun queryForecast(whereClause: String?, whereArgs: Array<String>?): WeatherCursorWrapper {
        val cursor = mDatabase.query(
            WeatherTable.NAME, // having
            null  // orderBy
            , // Columns - null selects all columns
            whereClause,
            whereArgs, null, null, null
        )// groupBy
        return WeatherCursorWrapper(cursor)
    }

    private fun getContentValues(cityName: String, json: String): ContentValues {
        val values = ContentValues()
        values.put(CITY_NAME, cityName)
        values.put(JSON, json)
        return values
    }
}