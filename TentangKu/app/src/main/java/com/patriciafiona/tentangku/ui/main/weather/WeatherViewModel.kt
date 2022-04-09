package com.patriciafiona.tentangku.ui.main.weather

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.BuildConfig
import com.patriciafiona.tentangku.data.source.remote.ApiConfig
import com.patriciafiona.tentangku.data.source.remote.responses.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel(private val lat: Double, private val lon: Double) : ViewModel() {

    private val _weather = MutableLiveData<WeatherResponse>()
    val weather: LiveData<WeatherResponse> = _weather
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "WeatherViewModel"
        const val APIKey: String = BuildConfig.OPENWEATHERMAP_KEY
        const val units: String = "metric"
    }

    init {
        getWeatherNow()
    }

    private fun getWeatherNow() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCurrentWeather(APIKey, lat, lon, units)
        client.enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _weather.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
