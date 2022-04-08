package com.patriciafiona.tentangku.helper

import androidx.recyclerview.widget.DiffUtil
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction

class FinanceDiffCallback (private val mOldTransactionList: List<FinanceTransaction>,
                           private val mNewTransactionList: List<FinanceTransaction>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldTransactionList.size
    }
    override fun getNewListSize(): Int {
        return mNewTransactionList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldTransactionList[oldItemPosition].id == mNewTransactionList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldTransactionList[oldItemPosition]
        val newEmployee = mNewTransactionList[newItemPosition]
        return oldEmployee.nominal == newEmployee.nominal &&
                oldEmployee.type == newEmployee.type &&
                oldEmployee.date == newEmployee.date
    }
}