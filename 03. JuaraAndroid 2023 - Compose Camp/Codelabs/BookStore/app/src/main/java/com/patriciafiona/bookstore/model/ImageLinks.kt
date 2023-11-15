package com.patriciafiona.bookstore.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
) {
    val httpsThumbnail : String
        get() = thumbnail.replace("http", "https")
}
