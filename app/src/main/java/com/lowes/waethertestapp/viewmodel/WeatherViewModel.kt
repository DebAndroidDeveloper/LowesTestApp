package com.lowes.waethertestapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.lowes.waethertestapp.adapter.WeatherListAdapter
import com.lowes.waethertestapp.callback.IView
import com.lowes.waethertestapp.callback.IViewModel
import com.lowes.waethertestapp.model.List
import com.lowes.waethertestapp.model.WeatherData
import com.lowes.waethertestapp.network.WeatherService
import com.lowes.waethertestapp.util.CommonUtils.APP_ID
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class WeatherViewModel() : ViewModel(),IViewModel {
    private val TAG = "WeatherViewModel"
    private lateinit var mView: IView

    private lateinit var mWeatherService: WeatherService

    private lateinit var weatherListAdapter: WeatherListAdapter

    private var weatherForecastList = ArrayList<List>()

    override fun init(view: IView,listener: WeatherListAdapter.Listener ,weatherService: WeatherService, cityName: String) {
        this.mView = view
        this.mWeatherService = weatherService
        weatherListAdapter = WeatherListAdapter(listener)
        this.mWeatherService.getWeatherForecast(cityName, APP_ID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onError = {
                     mView.showError("Error Fetching Weather Forecast")
                    Log.e(TAG, it.message!!)
                    //weatherForecastList.clear()
                    //weatherListAdapter.setWeatherForecastList(weatherForecastList)
                    mView.displayNoResultsFound(true)
                },
                onNext = { weatherData ->
                    weatherData.list?.let {
                        weatherForecastList = it as ArrayList<List>
                            weatherListAdapter.setWeatherForecastList(weatherForecastList)
                            mView.hideProgressBar()
                            if(weatherListAdapter.itemCount > 0){
                                mView.displayNoResultsFound(false)
                            } else {
                                mView.displayNoResultsFound(true)
                            }
                        }
                }
            )
    }

    fun getAdapter() = weatherListAdapter
}