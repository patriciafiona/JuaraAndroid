package com.patriciafiona.learningforkids.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ColorData(
    val name: String = "",
    val images: HashMap<String, String> = HashMap<String, String>(),
    val color: String = ""
): Parcelable
