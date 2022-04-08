package com.patriciafiona.tentangku.ui.main.finance

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.databinding.ItemTransactionBinding
import com.patriciafiona.tentangku.databinding.ItemWeightBinding
import com.patriciafiona.tentangku.helper.FinanceDiffCallback
import com.patriciafiona.tentangku.helper.WeightDiffCallback
import com.patriciafiona.tentangku.ui.main.finance.addUpdate.FinanceAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.weight.WeightAdapter
import com.patriciafiona.tentangku.ui.main.weight.addUpdate.WeightAddUpdateActivity

class FinanceAdapter(private val activity: FinanceActivity) : RecyclerView.Adapter<FinanceAdapter.FinanceViewHolder>() {
    private val listTransactions = ArrayList<FinanceTransaction>()
    fun setListTransaction(listTransactions: List<FinanceTransaction>) {
        val diffCallback = FinanceDiffCallback(this.listTransactions, listTransactions)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listTransactions.clear()
        this.listTransactions.addAll(listTransactions)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinanceViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FinanceViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FinanceViewHolder, position: Int) {
        holder.bind(listTransactions[position])
    }
    override fun getItemCount(): Int {
        return listTransactions.size
    }
    inner class FinanceViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(data: FinanceTransaction) {
            with(binding) {
                tvItemNominal.text = Utils.setRupiahFormat(data.nominal!!.toDouble())
                tvItemDate.text = data.date
                tvItemDescription.text = data.description

                when(data.type){
                    "Income" -> {
                        tvItemTitle.text = activity.getString(R.string.income)
                        tvItemNominal.setTextColor( activity.getColor(R.color.green))
                        tvIcon.setImageDrawable(activity.getDrawable(R.drawable.income_icon))
                    }
                    "Outcome" -> {
                        tvItemTitle.text = activity.getString(R.string.outcome)
                        tvItemNominal.setTextColor( activity.getColor(R.color.red))
                        tvIcon.setImageDrawable(activity.getDrawable(R.drawable.outcome_icon))
                    }
                }

                cvItemTransaction.setOnClickListener {
                    val intent = Intent(it.context, FinanceAddUpdateActivity::class.java)
                    intent.putExtra(FinanceAddUpdateActivity.EXTRA_FINANCE, data)
                    it.context.startActivity(intent)
                }
            }
        }
    }
}