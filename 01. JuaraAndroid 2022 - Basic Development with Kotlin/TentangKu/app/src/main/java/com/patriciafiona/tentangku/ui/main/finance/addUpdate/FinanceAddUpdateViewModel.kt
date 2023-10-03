package com.patriciafiona.tentangku.ui.main.finance.addUpdate

import android.app.Application
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.data.source.local.repository.FinanceRepository

class FinanceAddUpdateViewModel (application: Application) : ViewModel() {
    private val mFinanceRepository: FinanceRepository = FinanceRepository(application)
    fun insert(data: FinanceTransaction) {
        mFinanceRepository.insert(data)
    }
    fun update(data: FinanceTransaction) {
        mFinanceRepository.update(data)
    }
    fun delete(data: FinanceTransaction) {
        mFinanceRepository.delete(data)
    }
}