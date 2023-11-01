package com.patriciafiona.learningforkids.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Alphabet(
    val name: String = "",
    val color: String = "",
    val color_dark: String = "",
    val img_url: String = "",
    val phonic: String = "",
    val videoId: String = ""
): Parcelable
