package com.patriciafiona.tentangku.data.source.remote

import com.patriciafiona.tentangku.data.source.remote.responses.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/weather")
    fun getCurrentWeather(
        @Query("appid") appid: String,
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String
    ): Call<WeatherResponse>
}