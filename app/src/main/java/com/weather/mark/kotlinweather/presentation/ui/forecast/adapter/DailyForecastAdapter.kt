package com.weather.mark.kotlinweather.presentation.ui.forecast.adapter

import android.support.annotation.NonNull
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.data.model.ItemListWeather
import kotlinx.android.synthetic.main.item_daily.view.*
import java.util.*

class DailyForecastAdapter(forecast: Forecast, @NonNull onItemClick: OnItemClick): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mOnItemClick: OnItemClick = onItemClick
    private val forecast: Forecast = forecast
    private val FORECAST_FOR_DAY : Int = 8

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val getInfoWeather = GetInfoWeather(forecast)
        val index = position * FORECAST_FOR_DAY
        holder.itemView.main.text = getInfoWeather.getDay(index)
        holder.itemView.description.text = getInfoWeather.getTemp(index)
        holder.itemView.icon.setImageResource(getInfoWeather.getIcon(index))
        holder.itemView.setOnClickListener(mInternalListener)
        holder.itemView.tag = forecast.list[index]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_daily, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return countOfWeatherDay()
    }

    fun countOfWeatherDay(): Int {
        var count = 0
        var day = 0
        val listWeathers = forecast.list

        for (i in listWeathers) {
            if (Date(listWeathers.get(day).dt * 1000L).getDay() == Date(i.dt * 1000L).getDay()) {
                continue
            }
            day = listWeathers.indexOf(i)
            count++
        }
        return count
    }

    private val mInternalListener = View.OnClickListener { view ->
        val itemListWeather = view.tag as ItemListWeather
        mOnItemClick.onItemClick(itemListWeather)
    }

    interface OnItemClick {
        fun onItemClick(itemListWeather: ItemListWeather)
    }
}

