package com.patriciafiona.tentangku.ui.main.weight

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.data.source.local.repository.NoteRepository
import com.patriciafiona.tentangku.data.source.local.repository.WeightRepository

class WeightViewModel (application: Application) : ViewModel() {
    private val mWeightRepository: WeightRepository = WeightRepository(application)
    fun getAllWeight(): LiveData<List<Weight>> = mWeightRepository.getAllWeights()
}