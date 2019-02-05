package com.weather.mark.kotlinweather.presentation.ui.forecast

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.weather.mark.kotlinweather.R
import com.weather.mark.kotlinweather.data.CityPreference
import com.weather.mark.kotlinweather.data.ForecastDAO
import com.weather.mark.kotlinweather.presentation.ui.listCity.ListCityActivity
import com.weather.mark.kotlinweather.presentation.util.GetInfoWeather
import kotlinx.android.synthetic.main.pagers_data_weather.*
import java.util.*

class ForecastActivity : AppCompatActivity() {
    private val cityNameList = ArrayList<String>()
    private var lastBackgroundColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagers_data_weather)
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    private fun updateData() {
        getListCity()

        if (cityNameList.size == 0) {
            startActivity(Intent(this, ListCityActivity::class.java))
        }

        setDataCityPagers()
    }

    private fun getListCity() {
        val dao = ForecastDAO(this)
        val forecasts = dao.getForecasts()
        cityNameList.clear()

        for (f in forecasts) {
            cityNameList.add(f.city.name)
        }
        cityNameList.sort()
    }

    private fun setDataCityPagers() {
        val city = CityPreference.getCityVisible(this)
        val fragmentManager = supportFragmentManager
        val context: Context = this
        weather_view_pager.adapter =
                ViewPagerAdapter(supportFragmentManager, cityNameList)

        weather_view_pager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(i: Int, v: Float, i1: Int) {}

            override fun onPageSelected(i: Int) {
                CityPreference.setCityVisible(context, cityNameList.get(i))
                setColor(i)
            }

            override fun onPageScrollStateChanged(i: Int) {}
        })
    }


    class ViewPagerAdapter internal constructor(fm: FragmentManager, private val city: ArrayList<String>)
        : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment? {
            return ForecastFragment.newInstance(city[position])
        }

        override fun getCount(): Int {
            return city.size
        }
    }

    private fun setColor(indexCity: Int) {
        val newColor = GetInfoWeather.getBackgroundColor(indexCity, this)

        if (lastBackgroundColor == 0) {
            weather_view_pager.setBackgroundColor(newColor)
        } else {
            val sunsetSkyAnimator = ObjectAnimator
                .ofInt(weather_view_pager, "backgroundColor", lastBackgroundColor, newColor)
                .setDuration(1000)
            sunsetSkyAnimator.setEvaluator(ArgbEvaluator())
            sunsetSkyAnimator.start()
        }

        lastBackgroundColor = newColor
    }
}

