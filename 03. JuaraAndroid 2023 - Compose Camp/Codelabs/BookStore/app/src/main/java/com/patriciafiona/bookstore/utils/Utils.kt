package com.patriciafiona.bookstore.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object Utils {
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
}