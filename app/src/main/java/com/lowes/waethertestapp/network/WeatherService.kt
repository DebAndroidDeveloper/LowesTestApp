package com.lowes.waethertestapp.network

import com.google.gson.Gson
import com.lowes.waethertestapp.model.WeatherData
import com.lowes.waethertestapp.util.CommonUtils.OPEN_WEATHER_API_URL
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WeatherService {
    @GET("/data/2.5/forecast")
    fun getWeatherForecast(@Query("q") city:String, @Query("appid") appId: String ): Observable<WeatherData>

    object WeatherServiceCreator {

        fun newService(gson: Gson): WeatherService {
            val httpClientBuilder = OkHttpClient.Builder()
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(interceptor)
            httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)
            httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
            val retrofit = Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClientBuilder.build())
                .build()
            return retrofit.create(WeatherService::class.java)
        }
    }

}