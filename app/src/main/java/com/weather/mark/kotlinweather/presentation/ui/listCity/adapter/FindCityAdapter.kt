package com.weather.mark.kotlinweather.presentation.ui.listCity.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.model.City
import com.weather.mark.kotlinweather.presentation.ui.forecast.adapter.ForecastHolder
import kotlinx.android.synthetic.main.item_city.view.*

class FindCityAdapter(var cities: List<City>, val context: Context, val clickItem: onClickItem) :
    RecyclerView.Adapter<ForecastHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastHolder {
        return ForecastHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_city, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        val city = cities[position]
        holder.itemView.main.text = city.name
        holder.itemView.description.text = city.regions
        holder.itemView.icon.visibility = View.GONE
        holder.itemView.delete.visibility = View.GONE
        holder.itemView.setOnClickListener {
            clickItem.clickItem(city)
        }
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    interface onClickItem {
        fun clickItem(city: City)
    }
}
