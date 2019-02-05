package com.weather.mark.kotlinweather.presentation.ui.listCity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.mydev.android.myweather.ui.list.AddCityFragment
import com.mydev.android.myweather.ui.list.ListUserFragment
import com.weather.mark.kotlinweather.R
import kotlinx.android.synthetic.main.activity_city.*

class ListCityActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city)
        supportFragmentManager.beginTransaction()
            .replace(R.id.rootView, ListUserFragment()).commit()
        add_city.setOnClickListener {
            add_city.hide()
            supportFragmentManager.beginTransaction()
                .replace(R.id.rootView, AddCityFragment()).commit()
        }

    }
}