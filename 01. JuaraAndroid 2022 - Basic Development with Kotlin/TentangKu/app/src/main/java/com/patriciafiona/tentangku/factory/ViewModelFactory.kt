package com.patriciafiona.tentangku.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.patriciafiona.tentangku.ui.main.finance.FinanceViewModel
import com.patriciafiona.tentangku.ui.main.finance.addUpdate.FinanceAddUpdateViewModel
import com.patriciafiona.tentangku.ui.main.notes.NoteViewModel
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateViewModel
import com.patriciafiona.tentangku.ui.main.reminder.ReminderViewModel
import com.patriciafiona.tentangku.ui.main.reminder.addUpdate.ReminderAddUpdateViewModel
import com.patriciafiona.tentangku.ui.main.weight.WeightViewModel
import com.patriciafiona.tentangku.ui.main.weight.addUpdate.WeightAddUpdateViewModel

class ViewModelFactory private constructor(private val mApplication: Application) : ViewModelProvider.NewInstanceFactory() {
    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(mApplication) as T
        } else if (modelClass.isAssignableFrom(NoteAddUpdateViewModel::class.java)) {
            return NoteAddUpdateViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(WeightViewModel::class.java)) {
            return WeightViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(WeightAddUpdateViewModel::class.java)) {
            return WeightAddUpdateViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(FinanceViewModel::class.java)) {
            return FinanceViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(FinanceAddUpdateViewModel::class.java)) {
            return FinanceAddUpdateViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
            return ReminderViewModel(mApplication) as T
        }else if (modelClass.isAssignableFrom(ReminderAddUpdateViewModel::class.java)) {
            return ReminderAddUpdateViewModel(mApplication) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}