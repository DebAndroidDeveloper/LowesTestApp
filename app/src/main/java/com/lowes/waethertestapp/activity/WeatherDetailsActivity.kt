package com.lowes.waethertestapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.lowes.waethertestapp.databinding.ActivityWeatherDetailsBinding
import com.lowes.waethertestapp.util.CommonUtils.FEELS_LIKE_TEMPERATURE
import com.lowes.waethertestapp.util.CommonUtils.TEMPERATURE
import com.lowes.waethertestapp.util.CommonUtils.WEATHER
import com.lowes.waethertestapp.util.CommonUtils.WEATHER_DESC

class WeatherDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherDetailsBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        binding.textViewTemp.text = intent.getStringExtra(TEMPERATURE)
        binding.textViewFeelsLike.text = intent.getStringExtra(FEELS_LIKE_TEMPERATURE)
        binding.textViewWeather.text = intent.getStringExtra(WEATHER)
        binding.textViewWeatherDescription.text = intent.getStringExtra(WEATHER_DESC)
    }
}