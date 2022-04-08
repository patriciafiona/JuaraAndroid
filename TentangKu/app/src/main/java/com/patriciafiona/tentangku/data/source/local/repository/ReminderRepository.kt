package com.patriciafiona.tentangku.data.source.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.patriciafiona.tentangku.data.source.local.dao.NoteDao
import com.patriciafiona.tentangku.data.source.local.dao.ReminderDao
import com.patriciafiona.tentangku.data.source.local.databases.NoteRoomDatabase
import com.patriciafiona.tentangku.data.source.local.databases.ReminderRoomDatabase
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ReminderRepository(application: Application) {
    private val mReminderDao: ReminderDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = ReminderRoomDatabase.getDatabase(application)
        mReminderDao = db.reminderDao()
    }
    fun getAllReminders(): LiveData<List<Reminder>> = mReminderDao.getAllReminder()
    fun insert(reminder: Reminder) {
        executorService.execute { mReminderDao.insert(reminder) }
    }
    fun delete(reminder: Reminder) {
        executorService.execute { mReminderDao.delete(reminder) }
    }
    fun update(reminder: Reminder) {
        executorService.execute { mReminderDao.update(reminder) }
    }
}