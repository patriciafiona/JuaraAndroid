package com.patriciafiona.tentangku

import android.annotation.SuppressLint
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
}