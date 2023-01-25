package com.patriciafiona.tentangku.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.patriciafiona.tentangku.navigation.NavigationBuilder
import com.patriciafiona.tentangku.ui.main.ui.theme.TentangKuTheme

class TentangKuApp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TentangKuTheme() {
                NavigationBuilder(appCompatActivity = this)
            }
        }
    }
}