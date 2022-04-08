package com.patriciafiona.tentangku.helper

import androidx.recyclerview.widget.DiffUtil
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder

class ReminderDiffCallback (private val mOldReminderList: List<Reminder>,
                            private val mNewReminderList: List<Reminder>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldReminderList.size
    }
    override fun getNewListSize(): Int {
        return mNewReminderList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldReminderList[oldItemPosition].id == mNewReminderList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldReminderList[oldItemPosition]
        val newEmployee = mNewReminderList[newItemPosition]
        return oldEmployee.title == newEmployee.title && oldEmployee.description == newEmployee.description
                && oldEmployee.type == newEmployee.type
    }
}