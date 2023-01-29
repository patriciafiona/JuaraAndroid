package com.patriciafiona.tentangku.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.content.DialogInterface
import android.content.Intent
import android.location.LocationManager
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.patriciafiona.tentangku.R
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


object Utils {
    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(type: String): String {
        when(type){
            "date" ->{
                val date = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                return formatter.format(date).toString()
            }
            "datetime" ->{
                val date = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
                return formatter.format(date).toString()
            }
        }
        return "-"
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentTime(): String {
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("HH:mm")
        return formatter.format(date).toString()
    }

    fun setRupiahFormat(nominal: Double): String{
        val formatter: NumberFormat = DecimalFormat("#,###")
        return if (nominal >= 0){
            "Rp.${formatter.format(nominal)}"
        }else{
            "- Rp.${formatter.format(Math.abs(nominal))}"
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromTimemillis(milliSeconds: Long, dateFormat: String?): String? {
        // Create a DateFormatter object for displaying date in specified format.
        val formatter = SimpleDateFormat(dateFormat)

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = milliSeconds
        return formatter.format(calendar.time)
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

    fun setBackgroundBaseOnTime(): Int{
        val c: Calendar = Calendar.getInstance()
        when (c.get(Calendar.HOUR_OF_DAY)) {
            in 0..11 -> {
                return R.drawable.gradient_morning
            }
            in 12..15 -> {
                return R.drawable.gradient_afternoon
            }
            in 16..20 -> {
                return R.drawable.gradient_evening
            }
            in 21..23 -> {
                return R.drawable.gradient_night
            }
        }
        return com.patriciafiona.tentangku.R.drawable.gradient_morning
    }

    fun Context.getActivity(): AppCompatActivity? = when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

    fun checkLocation(context: Context) {
        val lm: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
            Toast.makeText(
                context,
                context.getText(R.string.failed_get_gps_service_provider),
                Toast.LENGTH_SHORT
            ).show()
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
            Toast.makeText(
                context,
                context.getText(R.string.failed_get_network_service_provider),
                Toast.LENGTH_SHORT
            ).show()
        }

        if (!gpsEnabled && !networkEnabled) {
            // notify user
            AlertDialog.Builder(context)
                .setMessage(R.string.gps_network_not_enabled)
                .setPositiveButton(R.string.open_location_settings) { dialog, _ ->
                    context.startActivity(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    )

                    //close dialog
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel, null)
                .show()
        }
    }
}