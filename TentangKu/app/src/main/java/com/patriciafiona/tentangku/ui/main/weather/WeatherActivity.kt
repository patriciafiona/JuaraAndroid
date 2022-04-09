package com.patriciafiona.tentangku.ui.main.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var gpsTracker:GPSTracker

    private lateinit var viewModel: WeatherViewModel

    companion object {
        const val TAG = "lat_long_getter"
    }
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            //get lat long
            gpsTracker = GPSTracker(this@WeatherActivity)

            if (gpsTracker.getIsGPSTrackingEnabled()){
                Log.e(TAG, "Lat: ${gpsTracker.getLatitude()}, " +
                        "Long: ${gpsTracker.getLongitude()}, " +
                        "City: ${gpsTracker.getLocality(this@WeatherActivity)}")

                //get data from API
                viewModel = WeatherViewModel(gpsTracker.getLatitude(), gpsTracker.getLongitude())
                viewModel.weather.observe(this@WeatherActivity) { data ->
                    cityName.text = data.name.toString()
                    currentTemperature.text = "${data.main?.temp.toString()} \u2103"
                    weatherType.text = "${data.weather?.get(0)?.main.toString()} - ${data.weather?.get(0)?.description.toString()}"

                    windVal.text = "${data.wind?.speed.toString()} m/s"
                    humidityVal.text = "${data.main?.humidity.toString()}%"
                    pressureVal.text = "${data.main?.pressure.toString()} hPa"
                    lastUpdateVal.text = "${data.dt?.toLong()
                        ?.let { Utils.getDateFromTimemillis(it*1000, "EEE, d MMM yyyy HH:mm:ss") }}"

                    Glide.with(this@WeatherActivity)
                        .load("https://openweathermap.org/img/wn/${data.weather?.get(0)?.icon.toString()}@4x.png")
                        .error(R.drawable.ic_error)
                        .into(weatherIcon)
                }
            }else {
                gpsTracker.showSettingsAlert()
            }

            btnBack.setOnClickListener {
                super.onBackPressed()
            }
        }
    }
}