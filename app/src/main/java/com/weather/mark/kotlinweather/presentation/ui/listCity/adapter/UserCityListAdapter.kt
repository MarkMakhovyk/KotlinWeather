package com.weather.mark.kotlinweather.presentation.ui.listCity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.ForecastDAO
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.presentation.ui.forecast.adapter.ForecastHolder
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import kotlinx.android.synthetic.main.item_city.view.*

class UserCityListAdapter(list: List<Forecast>, context: Context) : RecyclerView.Adapter<ForecastHolder>() {
    private var list: List<Forecast> = list
    private var context: Context = context
    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): ForecastHolder {
        return ForecastHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_city, viewGroup, false)
        )
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ForecastHolder, i: Int) {
        val forecast = list[i]
        val getInfoWeather = GetInfoWeather(forecast)
        val fm = (getInfoWeather.getTemp(0) + ", "
                + forecast.list.get(0).weatherDescription[0].description)
        holder.itemView.description.setText(fm)
        holder.itemView.main.setText(forecast.city.name)
        holder.itemView.icon.setImageResource(getInfoWeather.getIcon(0))

        holder.itemView.delete.setOnClickListener {
            val dao = ForecastDAO(context)
            dao.deleteForecast(forecast.city.name)
            list = dao.getForecasts()
            this@UserCityListAdapter.notifyDataSetChanged()
        }
    }
}

