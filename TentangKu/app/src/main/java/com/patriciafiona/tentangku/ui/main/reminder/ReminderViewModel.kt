package com.patriciafiona.tentangku.ui.main.reminder

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.data.source.local.repository.ReminderRepository

class ReminderViewModel (application: Application) : ViewModel() {
    private val mReminderRepository: ReminderRepository = ReminderRepository(application)
    fun getAllReminder(): LiveData<List<Reminder>> = mReminderRepository.getAllReminders()
}