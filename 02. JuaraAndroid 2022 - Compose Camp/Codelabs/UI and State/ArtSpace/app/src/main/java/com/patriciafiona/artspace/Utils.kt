package com.patriciafiona.artspace

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Utils {
    @RequiresApi(Build.VERSION_CODES.O)
    fun DateFormater(date: String): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return LocalDate.parse(date).format(formatter)
    }
}