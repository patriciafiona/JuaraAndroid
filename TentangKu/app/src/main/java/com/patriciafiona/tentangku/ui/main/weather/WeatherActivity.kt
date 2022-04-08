package com.patriciafiona.tentangku.ui.main.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
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