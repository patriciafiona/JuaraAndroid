package com.patriciafiona.tentangku.data.source.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.patriciafiona.tentangku.data.source.local.dao.FinanceDao
import com.patriciafiona.tentangku.data.source.local.databases.FinanceRoomDatabase
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FinanceRepository(application: Application) {
    private val mFinanceDao: FinanceDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FinanceRoomDatabase.getDatabase(application)
        mFinanceDao = db.financeDao()
    }
    fun getAllTransaction(): LiveData<List<FinanceTransaction>> = mFinanceDao.getAllTransaction()
    fun insert(transaction: FinanceTransaction) {
        executorService.execute { mFinanceDao.insert(transaction) }
    }
    fun delete(transaction: FinanceTransaction) {
        executorService.execute { mFinanceDao.delete(transaction) }
    }
    fun update(transaction: FinanceTransaction) {
        executorService.execute { mFinanceDao.update(transaction) }
    }
}