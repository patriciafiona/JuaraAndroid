package com.patriciafiona.tentangku.data.source.local.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    val name: String = "",
    val icon: Int
): Parcelable
