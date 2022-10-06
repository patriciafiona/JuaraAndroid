package com.patriciafiona.tentangku.ui.main.weather

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.databinding.ActivityWeatherBinding
import java.util.*


class WeatherActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityWeatherBinding
    private lateinit var gpsTracker:GPSTracker
    private lateinit var mMap: GoogleMap


    private lateinit var viewModel: WeatherViewModel

    private var lat: Double = 0.0
    private var lon: Double = 0.0

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
                lat = gpsTracker.getLatitude()
                lon = gpsTracker.getLongitude()

                // Obtain the SupportMapFragment and get notified when the map is ready to be used.
                val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
                mapFragment.getMapAsync(this@WeatherActivity)

                //get data from API
                viewModel = WeatherViewModel(lat, lon)
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

                //set background base on current time
                setBackgroundBaseOnTime()
            }else {
                gpsTracker.showSettingsAlert()
            }

            btnBack.setOnClickListener {
                super.onBackPressed()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setBackgroundBaseOnTime(){
        val c: Calendar = Calendar.getInstance()
        with(binding) {
            when (c.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    weatherBg.background = getDrawable(R.drawable.gradient_morning)
                }
                in 12..15 -> {
                    weatherBg.background = getDrawable(R.drawable.gradient_afternoon)
                }
                in 16..20 -> {
                    weatherBg.background = getDrawable(R.drawable.gradient_evening)
                }
                in 21..23 -> {
                    weatherBg.background = getDrawable(R.drawable.gradient_night)
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val center = LatLng(lat, lon)
        mMap.addMarker(
            MarkerOptions()
            .position(center)
            .title("Current Location"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(center))

        val zoomLevel = 16.0f //This goes up to 21
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, zoomLevel))
    }
}