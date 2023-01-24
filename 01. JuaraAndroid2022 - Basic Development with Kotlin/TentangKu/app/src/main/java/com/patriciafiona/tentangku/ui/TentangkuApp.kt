package com.patriciafiona.tentangku.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.patriciafiona.tentangku.navigation.NavigationBuilder
import com.patriciafiona.tentangku.ui.main.ui.theme.TentangKuTheme

class TentangKuApp : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TentangKuTheme() {
                NavigationBuilder()
            }
        }
    }
}