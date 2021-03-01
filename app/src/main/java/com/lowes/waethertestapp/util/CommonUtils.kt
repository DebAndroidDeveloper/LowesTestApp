package com.lowes.waethertestapp.util

import android.content.Context
import android.net.ConnectivityManager


object CommonUtils {

    internal const val OPEN_WEATHER_API_URL = "https://api.openweathermap.org"
    internal const val APP_ID = "65d00499677e59496ca2f318eb68c049"
    internal const val CITY_NAME = "city_name"
    internal const val TEMPERATURE = "temperature"
    internal const val FEELS_LIKE_TEMPERATURE = "feels_like_temperature"
    internal const val WEATHER = "weather"
    internal const val WEATHER_DESC = "weather_desc"

    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }

    fun convertKelvinToFahrenheit(temperature: Double): Double {
        return 9/5 * (temperature - 273) + 32
    }
}