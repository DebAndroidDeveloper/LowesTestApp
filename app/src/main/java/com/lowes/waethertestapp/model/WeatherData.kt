package com.lowes.waethertestapp.model
import kotlin.collections.List

data class WeatherData(val cod : String,
                       val message : Int,
                       val cnt : Int,
                       val list : List<com.lowes.waethertestapp.model.List>?,
                       val city : City)
