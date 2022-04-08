package com.patriciafiona.tentangku.data.source.local.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patriciafiona.tentangku.data.source.local.dao.NoteDao
import com.patriciafiona.tentangku.data.source.local.dao.ReminderDao
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder

@Database(entities = [Reminder::class], version = 1)
abstract class ReminderRoomDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    companion object {
        @Volatile
        private var INSTANCE: ReminderRoomDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): ReminderRoomDatabase {
            if (INSTANCE == null) {
                synchronized(ReminderRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ReminderRoomDatabase::class.java, "reminder_database")
                        .build()
                }
            }
            return INSTANCE as ReminderRoomDatabase
        }
    }
}