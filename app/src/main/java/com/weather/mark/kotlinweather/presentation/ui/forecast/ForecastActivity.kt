package com.weather.mark.kotlinweather.presentation.ui.forecast

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.weather.mark.kotlinweather.R
import kotlinx.android.synthetic.main.pagers_data_weather.*

class ForecastActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pagers_data_weather)
        val city: ArrayList<String> = arrayListOf("moscow")

        weather_view_pager.adapter =
                ViewPagerAdapter(
                    supportFragmentManager,
                    city
                )
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
}

