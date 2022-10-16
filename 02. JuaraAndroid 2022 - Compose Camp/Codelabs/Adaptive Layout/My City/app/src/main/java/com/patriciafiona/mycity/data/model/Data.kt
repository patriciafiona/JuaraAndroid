package com.patriciafiona.mycity.data.model

import com.patriciafiona.mycity.data.MenuType

data class Data(
    val id: String,
    val name: String,
    val desc: String,
    val established: String? = null,
    val location: String,
    val category: String? = null,
    val websiteUrl: String? = null,
    val imageUrl: String,
    val type: MenuType
)
