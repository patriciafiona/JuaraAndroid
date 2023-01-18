package com.patriciafiona.tentangku.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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