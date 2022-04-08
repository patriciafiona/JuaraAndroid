package com.patriciafiona.tentangku.ui.main.reminder.addUpdate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.databinding.ActivityMainBinding
import com.patriciafiona.tentangku.databinding.ActivityReminderAddUpdateBinding
import com.patriciafiona.tentangku.databinding.ActivityReminderBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateViewModel
import java.text.SimpleDateFormat
import java.util.*

class ReminderAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderAddUpdateBinding

    companion object {
        const val EXTRA_REMINDER = "extra_reminder"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }
    private var isEdit = false
    private var reminder: Reminder? = null

    val myCalendar: Calendar = Calendar.getInstance()

    private lateinit var reminderAddUpdateViewModel: ReminderAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isOneTimeReminder(true)
        with(binding){
            //set spinner dropdown
            val dropdown = edtReminderType
            val items = resources.getStringArray(R.array.reminder_type)
            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    this@ReminderAddUpdateActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    items)
            dropdown.adapter = adapter

            edtReminderType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {}

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when(edtReminderType.getItemAtPosition(position)){
                        "One time" ->{
                            isOneTimeReminder(true)
                        }
                        "Daily" ->{
                            isOneTimeReminder(false)
                        }
                    }
                }

            }

            //button on click listener
            btnBack.setOnClickListener {
                showAlertDialog(NoteAddUpdateActivity.ALERT_DIALOG_CLOSE)
            }
            btnDelete.setOnClickListener {
                showAlertDialog(NoteAddUpdateActivity.ALERT_DIALOG_DELETE)
            }

            val date =
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    myCalendar[Calendar.YEAR] = year
                    myCalendar[Calendar.MONTH] = month
                    myCalendar[Calendar.DAY_OF_MONTH] = day
                    updateLabel()
                }

            edtDate.setOnClickListener {
                val dialog = DatePickerDialog(
                    this@ReminderAddUpdateActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )
                dialog.datePicker.minDate = Date().time
                dialog.show()
            }

            val timePicker: TimePickerDialog = TimePickerDialog(
                this@ReminderAddUpdateActivity,
                timePickerDialogListener,
                12,
                10,
                true
            )

            edtTime.setOnClickListener {
                timePicker.show()
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtDate.setText(dateFormat.format(myCalendar.time))
    }

    private fun isOneTimeReminder(status: Boolean){
        with(binding){
            textViewDate.isVisible = !status
            textInputLayoutTitle2.isVisible = !status
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): ReminderAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(ReminderAddUpdateViewModel::class.java)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showAlertDialog(NoteAddUpdateActivity.ALERT_DIALOG_CLOSE)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    reminderAddUpdateViewModel.delete(reminder as Reminder)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute -> // logic to properly handle
            // the picked timings by user
            val formattedTime: String =
                if (minute < 10) {
                    "${hourOfDay}:0${minute}"
                } else {
                    "${hourOfDay}:${minute}"
                }

            binding.edtTime.setText(formattedTime)
        }
}