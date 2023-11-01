package com.patriciafiona.learningforkids.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import java.net.InetAddress
import java.util.Calendar


object Utils {

    @Composable
    fun setStatusBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(
            color = color
        )
    }

    @Composable
    fun setNavigationBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setNavigationBarColor(
            color = color
        )
    }

    @Composable
    fun setSystemBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        systemUiController.setSystemBarsColor(
            color = color
        )
    }

    @Composable
    fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

    @Composable
    fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

    fun getColor(colorString: String): Color {
        return Color(android.graphics.Color.parseColor(colorString))
    }

    fun isInternetAvailable(): Boolean {
        return try {
            val ipAddr = InetAddress.getByName("google.com")
            return !ipAddr.equals("")
        } catch (e: Exception) {
            false
        }
    }

    fun getTimeGreetingStatus() : String {
        val c = Calendar.getInstance()

        when (c[Calendar.HOUR_OF_DAY]) {
            in 0..11 -> {
                return "Good Morning"
            }
            in 12..15 -> {
                return "Good Afternoon"
            }
            in 16..20 -> {
                return "Good Evening"
            }
            in 21..23 -> {
                return "Good Night"
            }
        }

        return "Unknown time"
    }

    @Composable
    fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
        val eventHandler = rememberUpdatedState(onEvent)
        val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

        DisposableEffect(lifecycleOwner.value) {
            val lifecycle = lifecycleOwner.value.lifecycle
            val observer = LifecycleEventObserver { owner, event ->
                eventHandler.value(owner, event)
            }

            lifecycle.addObserver(observer)
            onDispose {
                lifecycle.removeObserver(observer)
            }
        }
    }
}