package com.patriciafiona.bookstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(
    val id: String,
    val description: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
): Parcelable {
    fun getPrice(): String {
        if (saleInfo.listPrice == null) {
            return ""
        }
        return "${saleInfo.listPrice.amount} ${saleInfo.listPrice.currency}"
    }

}
