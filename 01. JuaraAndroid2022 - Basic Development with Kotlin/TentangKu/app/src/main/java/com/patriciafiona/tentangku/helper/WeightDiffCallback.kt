package com.patriciafiona.tentangku.helper

import androidx.recyclerview.widget.DiffUtil
import com.patriciafiona.tentangku.data.source.local.entity.Weight

class WeightDiffCallback(private val mOldWeightList: List<Weight>, private val mNewWeightList: List<Weight>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldWeightList.size
    }
    override fun getNewListSize(): Int {
        return mNewWeightList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldWeightList[oldItemPosition].id == mNewWeightList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldWeight = mOldWeightList[oldItemPosition]
        val newWeight = mNewWeightList[newItemPosition]
        return oldWeight.value == newWeight.value && oldWeight.date == newWeight.date
    }
}