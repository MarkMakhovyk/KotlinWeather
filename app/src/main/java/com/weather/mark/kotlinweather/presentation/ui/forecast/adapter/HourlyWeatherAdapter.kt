package com.weather.mark.kotlinweather.presentation.ui.forecast.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.data.model.ItemListWeather
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import kotlinx.android.synthetic.main.item_hourly.view.*
import java.util.*


class HourlyWeatherAdapter(var forecast: Forecast, var itemListWeather: ItemListWeather)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val COUNT_FORECAST_PER_DAY = 7
    private var startPosition: Int = forecast.list.indexOf(itemListWeather)
    private var count: Int = 0
    var listWeathers = forecast.list

    init{
        howCountForecastToday()
    }

    private fun howCountForecastToday() {
        var start = 0
        var finish = 0
        var ok = true
        var i = 0
        while (ok) {
            ok = false
            if (startPosition - i >= 0) {
                if (Date(listWeathers[startPosition - i].dt * 1000L).day
                    == Date(listWeathers[startPosition].dt * 1000L).day) {
                    start = startPosition - i
                    ok = true
                }
            }
            if (startPosition + i < listWeathers.size) {
                if (Date(listWeathers[startPosition + i].dt * 1000L).day
                    == Date(listWeathers[startPosition].dt * 1000L).day) {
                    finish = startPosition + i
                    ok = true
                }
            }
            i++
        }

        startPosition = start
        count = finish - start
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, p1: Int) {
        val getInfoWeather = GetInfoWeather(forecast)
        val index = startPosition + p1
        holder.itemView.main.setText(getInfoWeather.getTime(index))
        holder.itemView.description.setText(getInfoWeather.getTemp(index))
        holder.itemView.icon.setImageResource(getInfoWeather.getIcon(index))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_hourly, parent, false)
        )

    }

    override fun getItemCount(): Int {
        if (startPosition + COUNT_FORECAST_PER_DAY > listWeathers.size) {
            return listWeathers.size - startPosition
        } else if (count < COUNT_FORECAST_PER_DAY && count + COUNT_FORECAST_PER_DAY < listWeathers.size) {
            count = 7
        }
        return count
    }
}
