package com.patriciafiona.tentangku.ui.main.reminder.addUpdate

import android.app.Application
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.data.source.local.repository.NoteRepository
import com.patriciafiona.tentangku.data.source.local.repository.ReminderRepository

class ReminderAddUpdateViewModel (application: Application) : ViewModel() {
    private val mReminderAddUpdateViewModel: ReminderRepository = ReminderRepository(application)
    fun insert(reminder: Reminder) {
        mReminderAddUpdateViewModel.insert(reminder)
    }
    fun update(reminder: Reminder) {
        mReminderAddUpdateViewModel.update(reminder)
    }
    fun delete(reminder: Reminder) {
        mReminderAddUpdateViewModel.delete(reminder)
    }
}