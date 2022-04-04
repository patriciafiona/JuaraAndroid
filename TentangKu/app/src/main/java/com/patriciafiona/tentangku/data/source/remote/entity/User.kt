package com.patriciafiona.tentangku.data.source.remote.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String = "" ,
    val email: String = "",
    val lastLogin: String = "",
): Parcelable
