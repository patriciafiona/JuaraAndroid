package com.patriciafiona.bookstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class ListPrice(
    val amount: Float?,
    val currency: String? = ""
): Parcelable
