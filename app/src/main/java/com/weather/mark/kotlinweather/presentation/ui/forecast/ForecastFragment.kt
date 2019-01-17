package com.weather.mark.kotlinweather.presentation.ui.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.data.model.ItemListWeather
import com.weather.mark.kotlinweather.data.network.ApiClient
import com.weather.mark.kotlinweather.data.network.WeatherApi
import com.weather.mark.kotlinweather.presentation.ui.forecast.adapter.DailyForecastAdapter
import com.weather.mark.kotlinweather.presentation.ui.forecast.adapter.HourlyWeatherAdapter
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastFragment : Fragment(), DailyForecastAdapter.OnItemClick,  Callback<Forecast> {

    private val CITY_NAME = "cityName"
    private val TAG = "cityName"

    lateinit var forecast : Forecast

    lateinit var dayAdapter: DailyForecastAdapter
    lateinit var hourlyAdapter: HourlyWeatherAdapter
    lateinit var cityName: String
    lateinit var cityTextView: TextView
    lateinit var tempNow: TextView
    lateinit var icon_weather_now: ImageView
    lateinit var description: TextView
    lateinit var humidity: TextView
    lateinit var pressure: TextView
    lateinit var wind: TextView
    lateinit var clouds: TextView
    lateinit var swipe: SwipeRefreshLayout
    lateinit var settings: ImageButton
    lateinit var dailyRecyclerView : RecyclerView
    lateinit var hourlyRecyclerView : RecyclerView

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
        val view = inflater.inflate(R.layout.data_weather, container, false)

        cityTextView = view.findViewById(R.id.city)
        tempNow = view.findViewById(R.id.temp_now)
        icon_weather_now = view.findViewById(R.id.icon_weather_today)
        description = view.findViewById(R.id.description)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)
        wind = view.findViewById(R.id.wind)
        clouds = view.findViewById(R.id.clouds)
        swipe = view.findViewById(R.id.swipe)
        settings = view.findViewById(R.id.city_list)
        dailyRecyclerView = view.findViewById(R.id.recycler_view_weather_daily)
        hourlyRecyclerView = view.findViewById(R.id.recycler_view_weather_hourly)

var service: ApiClient = ApiClient()
        service.getWeatherApi().getData(
        cityName,
            WeatherApi.Const.LAND,
            WeatherApi.Const.API_KEY
        ).enqueue(this)

        return view
    }
    override fun onFailure(call: Call<Forecast>, t: Throwable) {
        Toast.makeText(context,"problem", Toast.LENGTH_LONG).show()
    }

    override fun onResponse(call: Call<Forecast>, response: Response<Forecast>) {
        if (response != null) {
            forecast = response.body() as Forecast
        }
        setDataForecast()
    }

    fun setDataForecast(){
        Log.e(TAG,"setData*******************************************")
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
        icon_weather_now.setImageResource(getInfoWeather.getIcon(0))
        humidity.text = (getInfoWeather.getWeatherHumidity(0))
        pressure.text = (getInfoWeather.getWeatherPressure(0))
        wind.text = (getInfoWeather.getWeatherWind(0))
        clouds.text = (getInfoWeather.getWeatherClouds(0))
        cityTextView.text = (forecast.city.name)
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
