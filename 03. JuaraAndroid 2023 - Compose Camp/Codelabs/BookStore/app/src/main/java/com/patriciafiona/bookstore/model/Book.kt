package com.patriciafiona.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val id: String,
    val description: String,
    val volumeInfo: VolumeInfo,
    val saleInfo: SaleInfo
) {
    fun getPrice(): String {
        if (saleInfo.listPrice == null) {
            return ""
        }
        return "${saleInfo.listPrice.amount} ${saleInfo.listPrice.currency}"
    }

}
