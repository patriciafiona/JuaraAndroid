package com.patriciafiona.tentangku

import android.annotation.SuppressLint
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
}