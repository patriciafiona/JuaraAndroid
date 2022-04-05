package com.patriciafiona.tentangku.data.source.local.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.patriciafiona.tentangku.data.source.local.dao.NoteDao
import com.patriciafiona.tentangku.data.source.local.dao.WeightDao
import com.patriciafiona.tentangku.data.source.local.databases.NoteRoomDatabase
import com.patriciafiona.tentangku.data.source.local.databases.WeightRoomDatabase
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class WeightRepository(application: Application) {
    private val mWeightDao: WeightDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = WeightRoomDatabase.getDatabase(application)
        mWeightDao = db.weightDao()
    }
    fun getAllWeights(): LiveData<List<Weight>> = mWeightDao.getAllWeights()
    fun insert(weight: Weight) {
        executorService.execute { mWeightDao.insert(weight) }
    }
    fun delete(weight: Weight) {
        executorService.execute { mWeightDao.delete(weight) }
    }
    fun update(weight: Weight) {
        executorService.execute { mWeightDao.update(weight) }
    }
}