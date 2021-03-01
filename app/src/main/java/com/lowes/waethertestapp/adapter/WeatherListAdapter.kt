package com.lowes.waethertestapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lowes.waethertestapp.databinding.LayoutWeatherRecyclerviewItemBinding
import com.lowes.waethertestapp.model.List
import com.lowes.waethertestapp.util.CommonUtils

class WeatherListAdapter(private val mListener: Listener):
    RecyclerView.Adapter<WeatherListAdapter.WeatherAdapterViewHolder>() {

    private val weatherForeCastList = ArrayList<List>()

    private lateinit var binding: LayoutWeatherRecyclerviewItemBinding

    interface Listener {
        fun onRowItemClicked(weatherDetails: List)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherAdapterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = LayoutWeatherRecyclerviewItemBinding.inflate(layoutInflater)
        return WeatherAdapterViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: WeatherAdapterViewHolder, position: Int) {
        val weatherList = weatherForeCastList[position].weather
        weatherList?.let {
            binding.textviewWeatherDesc.text = it[0].main
        }
        val temperature = weatherForeCastList[position].main.temp
        binding.textviewWaetherTemperature.text = CommonUtils.convertKelvinToFahrenheit(temperature).toString()
        holder.itemView.setOnClickListener(createClickListener(weatherForeCastList[position]))
    }

    override fun getItemCount(): Int {
        return weatherForeCastList.size
    }

    fun setWeatherForecastList(weatherList: ArrayList<List>) {
        weatherForeCastList.clear()
        weatherForeCastList.addAll(weatherList)
        this.notifyDataSetChanged()
    }

    private fun createClickListener(weatherDetails: List): View.OnClickListener {
        return View.OnClickListener { mListener.onRowItemClicked(weatherDetails) }
    }

    class WeatherAdapterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}