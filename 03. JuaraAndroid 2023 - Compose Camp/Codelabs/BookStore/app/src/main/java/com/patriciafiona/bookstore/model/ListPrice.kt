package com.patriciafiona.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
data class ListPrice(
    val amount: Float?,
    val currency: String? = ""
)
