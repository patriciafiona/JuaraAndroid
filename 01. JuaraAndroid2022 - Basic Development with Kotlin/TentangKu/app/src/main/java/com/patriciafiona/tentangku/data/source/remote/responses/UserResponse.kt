package com.patriciafiona.tentangku.data.source.remote.responses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserResponse(
    val name: String = "" ,
    val email: String = "",
    val lastLogin: String = "",
): Parcelable
