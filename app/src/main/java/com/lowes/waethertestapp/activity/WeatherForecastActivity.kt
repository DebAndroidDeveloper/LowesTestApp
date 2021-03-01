package com.lowes.waethertestapp.activity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.lowes.waethertestapp.R
import com.lowes.waethertestapp.adapter.WeatherListAdapter
import com.lowes.waethertestapp.callback.IView
import com.lowes.waethertestapp.databinding.ActivityWeatherForecastBinding
import com.lowes.waethertestapp.model.List
import com.lowes.waethertestapp.network.WeatherService
import com.lowes.waethertestapp.util.CommonUtils
import com.lowes.waethertestapp.util.CommonUtils.CITY_NAME
import com.lowes.waethertestapp.util.CommonUtils.FEELS_LIKE_TEMPERATURE
import com.lowes.waethertestapp.util.CommonUtils.TEMPERATURE
import com.lowes.waethertestapp.util.CommonUtils.WEATHER
import com.lowes.waethertestapp.util.CommonUtils.WEATHER_DESC
import com.lowes.waethertestapp.viewmodel.WeatherViewModel

class WeatherForecastActivity : AppCompatActivity(),IView, WeatherListAdapter.Listener {
    private lateinit var binding: ActivityWeatherForecastBinding

    private val viewModel by lazy {
        ViewModelProvider(this).get(WeatherViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherForecastBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.recyclerViewWeatherForecast.setHasFixedSize(true)
        val itemDecoration: RecyclerView.ItemDecoration = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        binding.recyclerViewWeatherForecast.addItemDecoration(itemDecoration)
        val cityName = intent.getStringExtra(CITY_NAME)
        cityName?.let {
            viewModel.init(this,this, WeatherService.WeatherServiceCreator.newService(Gson()),it)
        }
        binding.recyclerViewWeatherForecast.adapter = viewModel.getAdapter()
    }

    override fun displayNoResultsFound(noResults: Boolean) {
        if(noResults){
            binding.textViewNoResult.visibility = View.VISIBLE
        }else{
            binding.textViewNoResult.visibility = View.GONE
        }
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun showError(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show()
    }

    override fun onRowItemClicked(weatherDetails: List) {
        val intent = Intent(this, WeatherDetailsActivity::class.java)
        val temperature = CommonUtils.convertKelvinToFahrenheit(weatherDetails.main.temp).toString()
        val feelsLikeTemp = "Feels Like: ".plus(CommonUtils.convertKelvinToFahrenheit(weatherDetails.main.feels_like))
        intent.putExtra(TEMPERATURE, temperature)
        intent.putExtra(FEELS_LIKE_TEMPERATURE, feelsLikeTemp)
        val weatherList = weatherDetails.weather
        weatherList?.let {
            intent.putExtra(WEATHER,it[0].main)
            intent.putExtra(WEATHER_DESC,it[0].description)
        }
        this.startActivity(intent)
    }

    override fun showErrorDialog(message: String) {
        val alertDialog = AlertDialog.Builder(this).create()
        alertDialog.setTitle("Error")
        alertDialog.setMessage(message)
        alertDialog.setButton(
            AlertDialog.BUTTON_NEUTRAL, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }
}