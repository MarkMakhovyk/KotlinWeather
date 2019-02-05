package com.weather.mark.kotlinweather.data.city_ua

import android.content.Context
import com.weather.mark.kotlinweather.data.model.City
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

class CityList(context: Context) {
    internal var context: Context = context

    fun getCityList(): List<City> {
        val jsonText = loadJSONFromAsset()
        return parseJsonToObject(jsonText)
    }

    private fun parseJsonToObject(jsonText: String): ArrayList<City> {
        val cities = ArrayList<City>()
        try {
            val jsonAll = JSONObject(jsonText)
            val listRegion = jsonAll.getJSONObject("regions").getJSONArray("region")
            for (indexRegion in 0 until listRegion.length()) {
                val region = listRegion.getJSONObject(indexRegion)
                val cityByRegion = region.getJSONArray("city")
                val regionName = region.getString("_name") + ",UA"
                for (indexCity in 0 until cityByRegion.length()) {
                    val city = cityByRegion.getJSONObject(indexCity)
                    cities.add(
                        City(
                            city.getString("_name"),
                            city.getString("_lat"),
                            city.getString("_lon"),
                            regionName

                        )
                    )
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return cities
    }

    private fun loadJSONFromAsset(): String {
        var json: String
        try {
            val inputStream = context.assets.open("cityUA.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charset.forName("UTF-8"))


        } catch (ex: IOException) {
            ex.printStackTrace()
            return ""
        }
        return json
    }

    fun query(cities: List<City>, query: String): List<City> {
        val queryList = ArrayList<City>()
        for (c in cities) {
            if (c.name.toLowerCase().contains(query.toLowerCase())) {
                queryList.add(c)
            }
        }
        return queryList
    }
}