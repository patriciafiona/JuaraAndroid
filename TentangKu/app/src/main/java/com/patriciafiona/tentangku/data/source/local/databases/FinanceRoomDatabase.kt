package com.patriciafiona.tentangku.data.source.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patriciafiona.tentangku.data.source.local.dao.FinanceDao
import com.patriciafiona.tentangku.data.source.local.dao.NoteDao
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.data.source.local.entity.Note

@Database(entities = [FinanceTransaction::class], version = 1)
abstract class FinanceRoomDatabase : RoomDatabase() {
    abstract fun financeDao(): FinanceDao
    companion object {
        @Volatile
        private var INSTANCE: FinanceRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): FinanceRoomDatabase {
            if (INSTANCE == null) {
                synchronized(FinanceRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FinanceRoomDatabase::class.java, "finance_database")
                        .build()
                }
            }
            return INSTANCE as FinanceRoomDatabase
        }
    }
}