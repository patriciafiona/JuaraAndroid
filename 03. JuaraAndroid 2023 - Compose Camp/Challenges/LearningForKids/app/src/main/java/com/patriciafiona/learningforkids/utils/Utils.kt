package com.patriciafiona.learningforkids.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patriciafiona.learningforkids.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


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