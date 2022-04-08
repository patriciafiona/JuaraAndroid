package com.patriciafiona.tentangku.ui.main.reminder

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.databinding.ActivityReminderBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.finance.FinanceAdapter
import com.patriciafiona.tentangku.ui.main.finance.FinanceViewModel
import com.patriciafiona.tentangku.ui.main.reminder.addUpdate.ReminderAddUpdateActivity
import java.text.SimpleDateFormat
import java.util.*

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    private fun init(){
        checkReminderAvailability(false)

        with(binding){
            val reminderViewModel = obtainViewModel(this@ReminderActivity)
            reminderViewModel.getAllReminder().observe(this@ReminderActivity) { reminderList ->
                if (reminderList != null  && reminderList.isNotEmpty()) {
                    val sortedList = reminderList.sortedByDescending { data -> data.date }
                    adapter.setListReminders(sortedList)
                    checkReminderAvailability(true)
                }else{
                    checkReminderAvailability(false)
                }
            }

            adapter = ReminderAdapter(this@ReminderActivity)
            rvReminder.layoutManager = LinearLayoutManager(this@ReminderActivity)
            rvReminder.setHasFixedSize(true)
            rvReminder.adapter = adapter

            adapter.setOnItemClickCallback(object : ReminderAdapter.OnItemClickCallback {
                override fun onItemClicked(data: Reminder) {
                    val intent = Intent(this@ReminderActivity, ReminderAddUpdateActivity::class.java)
                    intent.putExtra(ReminderAddUpdateActivity.EXTRA_REMINDER, data)
                    startActivity(intent)
                }
            })

            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            btnAddReminder.setOnClickListener {
                val intent = Intent(this@ReminderActivity, ReminderAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ReminderViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ReminderViewModel::class.java)
    }

    private fun checkReminderAvailability(status: Boolean){
        with(binding){
            rvReminder.isVisible = status
            noFileImg.isVisible = !status
            noFileTxt.isVisible = !status
        }
    }
}