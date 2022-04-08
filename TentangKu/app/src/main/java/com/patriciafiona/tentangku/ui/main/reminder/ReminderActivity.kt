package com.patriciafiona.tentangku.ui.main.reminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.databinding.ActivityReminderBinding
import com.patriciafiona.tentangku.ui.main.notes.NoteAdapter
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.reminder.addUpdate.ReminderAddUpdateActivity

class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var adapter: ReminderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkReminderAvailability(false)

        with(binding){
            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            btnAddReminder.setOnClickListener {
                val intent = Intent(this@ReminderActivity, ReminderAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkReminderAvailability(status: Boolean){
        with(binding){
            rvReminder.isVisible = status
            noFileImg.isVisible = !status
            noFileTxt.isVisible = !status
        }
    }
}