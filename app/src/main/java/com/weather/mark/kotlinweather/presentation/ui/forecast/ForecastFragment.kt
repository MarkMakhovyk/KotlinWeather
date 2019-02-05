package com.weather.mark.kotlinweather.presentation.ui.forecast

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.ForecastDAO
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.data.model.ItemListWeather
import com.weather.mark.kotlinweather.presentation.ui.forecast.adapter.DailyForecastAdapter
import com.weather.mark.kotlinweather.presentation.ui.forecast.adapter.HourlyWeatherAdapter
import com.weather.mark.kotlinweather.presentation.ui.listCity.ListCityActivity
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import kotlinx.android.synthetic.main.data_weather.*

class ForecastFragment : Fragment(), DailyForecastAdapter.OnItemClick {

    private val CITY_NAME = "cityName"
    lateinit var forecast : Forecast

    lateinit var dayAdapter: DailyForecastAdapter
    lateinit var hourlyAdapter: HourlyWeatherAdapter
    lateinit var cityName: String


    companion object {
        private val CITY_NAME = "cityName"
        fun newInstance(city: String): Fragment {
            val args = Bundle()
            args.putSerializable(CITY_NAME, city)
            val fragment = ForecastFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cityName = arguments!!.getString(CITY_NAME)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.data_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityUserList.setOnClickListener {
            startActivity(Intent(context, ListCityActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        val forecastDAO = ForecastDAO(this!!.context!!)
        forecast = forecastDAO.getForecast(cityName)!!
        setDataForecast()
    }


    fun setDataForecast(){
        dailyRecyclerView.layoutManager = GridLayoutManager(context, 5)
        hourlyRecyclerView.layoutManager = GridLayoutManager(context, 7)
        setDataRecycleView()
        setDataWeatherToday()
    }

    private fun setDataRecycleView() {
        dayAdapter =
                DailyForecastAdapter(forecast, this)
        dailyRecyclerView.layoutManager = GridLayoutManager(context, dayAdapter.countOfWeatherDay())

        dailyRecyclerView.adapter = dayAdapter
        hourlyAdapter = HourlyWeatherAdapter(
            forecast,
            forecast.list[0]
        )
        hourlyRecyclerView.adapter =hourlyAdapter
    }

    private fun setDataWeatherToday() {
        var getInfoWeather = GetInfoWeather(forecast)
        tempNow.text = (getInfoWeather.getTemp(0))
        description.text = (getInfoWeather.getDescription(0))
        iconWeatherNow.setImageResource(getInfoWeather.getIcon(0))
        humidity.text = (getInfoWeather.getWeatherHumidity(0))
        pressure.text = (getInfoWeather.getWeatherPressure(0))
        wind.text = (getInfoWeather.getWeatherWind(0))
        clouds.text = (getInfoWeather.getWeatherClouds(0))
        cityNameTextView.text = (forecast.city.name)
    }

    override fun onItemClick(itemListWeather: ItemListWeather) {
        if (itemListWeather != null) {
            hourlyAdapter = HourlyWeatherAdapter(
                forecast,
                itemListWeather
            )
            hourlyRecyclerView.adapter = hourlyAdapter
        }
    }
}
