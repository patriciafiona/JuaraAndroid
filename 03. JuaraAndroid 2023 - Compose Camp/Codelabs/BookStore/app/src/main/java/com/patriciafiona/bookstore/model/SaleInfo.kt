package com.patriciafiona.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
data class SaleInfo(
    val country: String,
    val isEbook: Boolean,
    val listPrice: ListPrice?
) {
    // Notes: This works...
    val getPrice2 : String
        get() = "${listPrice?.amount ?: "N/A"} ${listPrice?.currency ?: "N/A"}"

}
