package com.weather.mark.kotlinweather.data.bd

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.weather.mark.kotlinweather.data.bd.WeatherDbSchema.WeatherTable.NAME

class WeatherBaseHelper(context: Context) : SQLiteOpenHelper(context, this.DATABASE_NAME, null, this.VERSION) {
    companion object {
        private val VERSION = 1
        private val DATABASE_NAME = "weatherBase.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table " + NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    WeatherDbSchema.WeatherTable.Cols.CITY_NAME + ", " +
                    WeatherDbSchema.WeatherTable.Cols.JSON + ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}
}