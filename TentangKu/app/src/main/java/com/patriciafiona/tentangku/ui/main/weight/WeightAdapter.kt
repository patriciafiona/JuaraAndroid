package com.patriciafiona.tentangku.ui.main.weight

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.databinding.ItemNoteBinding
import com.patriciafiona.tentangku.databinding.ItemWeightBinding
import com.patriciafiona.tentangku.helper.NoteDiffCallback
import com.patriciafiona.tentangku.helper.WeightDiffCallback
import com.patriciafiona.tentangku.ui.main.notes.NoteAdapter
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.weight.addUpdate.WeightAddUpdateActivity

class WeightAdapter : RecyclerView.Adapter<WeightAdapter.WeightViewHolder>() {
    private val lisWeights = ArrayList<Weight>()
    fun setListWeight(lisWeights: List<Weight>) {
        val diffCallback = WeightDiffCallback(this.lisWeights, lisWeights)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.lisWeights.clear()
        this.lisWeights.addAll(lisWeights)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeightAdapter.WeightViewHolder {
        val binding = ItemWeightBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeightViewHolder(binding)
    }
    override fun onBindViewHolder(holder: WeightViewHolder, position: Int) {
        holder.bind(lisWeights[position])
    }
    override fun getItemCount(): Int {
        return lisWeights.size
    }
    inner class WeightViewHolder(private val binding: ItemWeightBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Weight) {
            with(binding) {
                tvItemWeightVal.text = "${data.value} Kg"
                tvItemDate.text = data.date
                cvItemWeight.setOnClickListener {
                    val intent = Intent(it.context, WeightAddUpdateActivity::class.java)
                    intent.putExtra(WeightAddUpdateActivity.EXTRA_WEIGHT, data)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}