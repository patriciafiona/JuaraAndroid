package com.patriciafiona.tentangku.ui.main.about

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityAboutBinding
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.databinding.ActivitySplashBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}