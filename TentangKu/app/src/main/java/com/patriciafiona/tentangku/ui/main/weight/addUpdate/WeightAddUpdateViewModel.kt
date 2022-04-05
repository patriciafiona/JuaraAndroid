package com.patriciafiona.tentangku.ui.main.weight.addUpdate

import android.app.Application
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.data.source.local.repository.WeightRepository

class WeightAddUpdateViewModel (application: Application) : ViewModel() {
    private val mWeightRepository: WeightRepository = WeightRepository(application)
    fun insert(weight: Weight) {
        mWeightRepository.insert(weight)
    }
    fun update(weight: Weight) {
        mWeightRepository.update(weight)
    }
    fun delete(weight: Weight) {
        mWeightRepository.delete(weight)
    }
}