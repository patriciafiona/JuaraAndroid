package com.patriciafiona.tentangku.data.source.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patriciafiona.tentangku.data.source.local.dao.NoteDao
import com.patriciafiona.tentangku.data.source.local.dao.WeightDao
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Weight

@Database(entities = [Weight::class], version = 1)
abstract class WeightRoomDatabase : RoomDatabase() {
    abstract fun weightDao(): WeightDao
    companion object {
        @Volatile
        private var INSTANCE: WeightRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): WeightRoomDatabase {
            if (INSTANCE == null) {
                synchronized(WeightRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WeightRoomDatabase::class.java, "weight_database")
                        .build()
                }
            }
            return INSTANCE as WeightRoomDatabase
        }
    }
}