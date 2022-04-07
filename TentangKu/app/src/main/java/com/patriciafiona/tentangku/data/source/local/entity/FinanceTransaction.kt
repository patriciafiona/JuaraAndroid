package com.patriciafiona.tentangku.data.source.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class FinanceTransaction(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "type")
    var type: String? = null,

    @ColumnInfo(name = "nominal")
    var nominal: Double? = 0.0,

    @ColumnInfo(name = "description")
    var description: String? = null,

    @ColumnInfo(name = "date")
    var date: String? = null
) : Parcelable