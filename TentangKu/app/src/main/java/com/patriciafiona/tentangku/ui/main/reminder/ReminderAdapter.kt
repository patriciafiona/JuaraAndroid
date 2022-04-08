package com.patriciafiona.tentangku.ui.main.reminder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.databinding.ItemReminderBinding
import com.patriciafiona.tentangku.databinding.ItemTransactionBinding
import com.patriciafiona.tentangku.helper.FinanceDiffCallback
import com.patriciafiona.tentangku.helper.ReminderDiffCallback
import com.patriciafiona.tentangku.ui.main.finance.FinanceActivity
import com.patriciafiona.tentangku.ui.main.finance.FinanceAdapter
import com.patriciafiona.tentangku.ui.main.finance.addUpdate.FinanceAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.home.MenuAdapter
import com.patriciafiona.tentangku.ui.main.reminder.addUpdate.ReminderAddUpdateActivity

class ReminderAdapter(private val activity: ReminderActivity) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {
    private val listReminders = ArrayList<Reminder>()
    fun setListReminders(listReminders: List<Reminder>) {
        val diffCallback = ReminderDiffCallback(this.listReminders, listReminders)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listReminders.clear()
        this.listReminders.addAll(listReminders)
        diffResult.dispatchUpdatesTo(this)
    }

    private lateinit var alarmReceiver: AlarmReceiver
    private var reminder: Reminder? = null
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder{
        val binding = ItemReminderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        alarmReceiver = AlarmReceiver()
        return ReminderViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.bind(listReminders[position], position)
    }
    override fun getItemCount(): Int {
        return listReminders.size
    }
    inner class ReminderViewHolder(private val binding: ItemReminderBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
        fun bind(data: Reminder, position: Int) {
            with(binding) {
                tvItemTitle.text = data.type

                if (data.type.equals("Daily")) {
                    tvItemDate.text = "Every ${data.time}"

                    btnOnOffAlarm.isVisible = true
                    btnOnOffAlarm.isChecked = true
                }else{
                    tvItemDate.text = "${data.date} on ${data.time}"
                    btnOnOffAlarm.isVisible = false
                }
                tvItemDescription.text = data.description

                btnOnOffAlarm.setOnCheckedChangeListener { _, isChecked ->
                    reminder.let { reminder ->
                        reminder?.id = data.id
                        reminder?.time = data.time
                        reminder?.description = data.description
                        reminder?.type = data.type
                        reminder?.date = data.date
                    }

                    if (isChecked){
                        // turn of reminder
                        reminder?.let {
                            alarmReceiver.cancelAlarm(
                                activity,
                                it.id)
                        }
                    } else {
                        // turn on again
                        alarmReceiver.setRepeatingAlarm(activity, AlarmReceiver.TYPE_REPEATING,
                            data.time.toString(),
                            data.description.toString(),
                            data.id)
                    }
                }

                cvItemReminder.setOnClickListener { onItemClickCallback.onItemClicked(listReminders[position]) }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Reminder)
    }
}