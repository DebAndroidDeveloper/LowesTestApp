package com.lowes.waethertestapp.callback

import android.view.View
import com.lowes.waethertestapp.adapter.WeatherListAdapter
import com.lowes.waethertestapp.network.WeatherService

interface IViewModel {
    fun init(view: IView, listener: WeatherListAdapter.Listener,weatherService: WeatherService, cityName: String)
}