package com.patriciafiona.learningforkids.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alphabet(
    val name: String = "",
    val color: String = "#FFFFFF",
    val color_dark: String = "#000000",
    val img_url: String = "",
    val phonic: String = "",
    val videoId: String = ""
): Parcelable
