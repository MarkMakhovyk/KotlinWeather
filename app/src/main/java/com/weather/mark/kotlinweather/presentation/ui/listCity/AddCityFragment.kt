package com.mydev.android.myweather.ui.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.CityPreference
import com.weather.mark.kotlinweather.data.ForecastDAO
import com.weather.mark.kotlinweather.data.city_ua.CityList
import com.weather.mark.kotlinweather.data.model.City
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.data.network.GetForecast
import com.weather.mark.kotlinweather.presentation.ui.listCity.adapter.FindCityAdapter
import kotlinx.android.synthetic.main.layout_add_city.*

class AddCityFragment : Fragment(), GetForecast.CallbackLoadForecast, FindCityAdapter.onClickItem {

    private var cities: List<City>? = null
    private var cityList: CityList? = null
    private val getForecast = GetForecast(this)
    private lateinit var forecastDAO: ForecastDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.layout_add_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        forecastDAO = ForecastDAO(context!!)

        setListener()

        addCityList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        cityList = CityList(context!!)
        cities = cityList!!.getCityList()
        updateUI(cities)
    }

    private fun setListener() {
        searchView!!.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(s: String): Boolean {
                findForecast(s)
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                updateUI(cityList!!.query(cities!!, s))
                return false
            }
        })

        back.setOnClickListener { activity!!.onBackPressed() }
    }


    internal fun updateUI(list: List<City>?) {
        addCityList.adapter = FindCityAdapter(list!!, context!!, this)
    }


    internal fun findForecast(cityName: String) {
        getForecast.getByCity(cityName)
    }

    override fun clickItem(city: City) {
        getForecast.getByLocation(city.lat, city.lon)
    }


    override fun onResponse(forecast: Forecast?) {
        if (forecast != null) {
            forecastDAO.addOrUpdate(forecast)
            CityPreference.setCityVisible(context!!, forecast.city.name)
        }
        activity!!.finish()
    }
}

