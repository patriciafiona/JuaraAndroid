package com.patriciafiona.bookstore.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
data class ImageLinks(
    val smallThumbnail: String,
    val thumbnail: String,
): Parcelable {
    val httpsThumbnail : String
        get() = thumbnail.replace("http", "https")
}
