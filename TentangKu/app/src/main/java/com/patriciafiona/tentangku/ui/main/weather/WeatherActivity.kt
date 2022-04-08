package com.patriciafiona.tentangku.ui.main.weather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.patriciafiona.tentangku.databinding.ActivityWeatherBinding

class WeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            //
        }
    }
}