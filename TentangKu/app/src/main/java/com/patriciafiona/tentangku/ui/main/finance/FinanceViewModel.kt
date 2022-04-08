package com.patriciafiona.tentangku.ui.main.finance

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.data.source.local.repository.FinanceRepository

class FinanceViewModel (application: Application) : ViewModel() {
    private val mFinanceRepository: FinanceRepository = FinanceRepository(application)
    fun getAllTransaction(): LiveData<List<FinanceTransaction>> = mFinanceRepository.getAllTransaction()
}