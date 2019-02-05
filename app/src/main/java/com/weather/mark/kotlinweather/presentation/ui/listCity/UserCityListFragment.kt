package com.mydev.android.myweather.ui.list

import android.location.Location
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.CityPreference
import com.weather.mark.kotlinweather.data.ForecastDAO
import com.weather.mark.kotlinweather.data.model.Forecast
import com.weather.mark.kotlinweather.data.network.GPSLocation
import com.weather.mark.kotlinweather.data.network.GetForecast
import com.weather.mark.kotlinweather.presentation.ui.listCity.adapter.UserCityListAdapter
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import kotlinx.android.synthetic.main.layout_show_list.*


class ListUserFragment : Fragment(), GPSLocation.MyLocation, GetForecast.CallbackLoadForecast {


    private var forecastList: ArrayList<Forecast>? = null
    private val getForecast = GetForecast(this)
    private lateinit var gpsLocation: GPSLocation
    private lateinit var cityListAdapter: UserCityListAdapter
    private lateinit var forecastDAO: ForecastDAO

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return LayoutInflater.from(context).inflate(R.layout.layout_show_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userCityList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        forecastDAO = ForecastDAO(context!!)
        forecastList = forecastDAO.getForecasts()
        cityListAdapter = UserCityListAdapter(forecastList!!, context!!)
        userCityList.adapter = cityListAdapter

        checkLocation()
        setListener()
    }

    private fun checkLocation() {
        if (CityPreference.getLastLocation(context!!) != null) {
            val forecast = forecastDAO.getForecast(CityPreference.getLastLocation(context!!)!!)
            setDataLocation(forecast!!)
            forecastList!!.remove(forecast)
            cityListAdapter.notifyDataSetChanged()
        }
    }


    private fun setListener() {
        aSwitchLocation!!.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                onLocation()
            }
        }
        back!!.setOnClickListener { activity!!.onBackPressed() }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_LOCATION_PERMISSIONS -> {
                if (gpsLocation.hasLocationPermission()) {
                    gpsLocation.getGPS()
                }
                super.onRequestPermissionsResult(
                    requestCode, permissions,
                    grantResults
                )
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    internal fun onLocation() {
        gpsLocation = GPSLocation(context, this)
        gpsLocation.addClient()
    }


    override fun returnLocation(location: Location) {
        getForecast.getByLocation(location.latitude.toString(), location.longitude.toString())
    }

    internal fun setDataLocation(forecast: Forecast) {
        val getInfoWeather = GetInfoWeather(forecast)
        val fm = (getInfoWeather.getTemp(0) + ", "
                + forecast.list[0].weatherDescription[0].description)
        description_location.text = fm
        main_location.text = forecast.city.name
        icon_location.setImageResource(getInfoWeather.getIcon(0))
        CityPreference.setLastLocation(context!!, forecast.city.name)
    }

    override fun permissions(permissions: Array<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity!!, permissions,
            requestCode
        )
    }

    override fun onResponse(forecast: Forecast?) {
        forecastDAO.addOrUpdate(forecast)
        if (forecast != null) {
            setDataLocation(forecast)
        }
    }

    companion object {
        private val REQUEST_LOCATION_PERMISSIONS = 0
    }
}
