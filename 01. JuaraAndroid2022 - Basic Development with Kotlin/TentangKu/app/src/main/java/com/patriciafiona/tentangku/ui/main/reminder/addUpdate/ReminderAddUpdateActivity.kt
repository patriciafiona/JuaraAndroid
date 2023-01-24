package com.patriciafiona.tentangku.ui.main.reminder.addUpdate

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.utils.Utils
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.databinding.ActivityReminderAddUpdateBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.reminder.AlarmReceiver
import java.text.SimpleDateFormat
import java.util.*

class ReminderAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderAddUpdateBinding

    companion object {
        const val EXTRA_REMINDER = "extra_reminder"
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
        const val TAG_REMINDER = "Reminder"
    }
    private var isEdit = false
    private var reminder: Reminder? = null

    private lateinit var alarmReceiver: AlarmReceiver
    val myCalendar: Calendar = Calendar.getInstance()

    private lateinit var reminderAddUpdateViewModel: ReminderAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isOneTimeReminder(true)
        alarmReceiver = AlarmReceiver()
        reminderAddUpdateViewModel = obtainViewModel(this)

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

            reminder = intent.getParcelableExtra(EXTRA_REMINDER)
            if (reminder != null) {
                isEdit = true
                btnAddUpdateReminder.isVisible = false
                addUpdateReminderTxt.text = getString(R.string.reminder_data)
            } else {
                reminder = Reminder()
                btnAddUpdateReminder.text = getString(R.string.add_reminder)
                addUpdateReminderTxt.text = getString(R.string.add_reminder)
            }

            if (isEdit) {
                if (reminder != null) {
                    reminder?.let { reminder ->
                        //set selected dropdown
                        if (reminder.type != null) {
                            val spinnerPosition = adapter.getPosition(reminder.type)
                            dropdown.setSelection(spinnerPosition)
                        }

                        edtDate.setText(reminder.date.toString())
                        edtTime.setText(reminder.time.toString())
                        edtDescription.setText(reminder.description.toString())
                        btnDelete.isVisible = true
                    }
                }
            } else {
                btnDelete.isVisible = false
                edtDate.setText(Utils.getCurrentDate("date"))
            }

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

            btnAddUpdateReminder.setOnClickListener {
                val typeVal = edtReminderType.selectedItem.toString().trim()
                val descVal = edtDescription.text.toString().trim()
                val dateVal = edtDate.text.toString().trim()
                val timeVal = edtTime.text.toString().trim()

                when {
                    descVal.isEmpty() -> {
                        edtDescription.error = getString(R.string.empty)
                    }
                    descVal.isEmpty() -> {
                        edtDescription.error = getString(R.string.empty)
                    }
                    timeVal.isEmpty() -> {
                        edtTime.error = getString(R.string.empty)
                    }
                    else -> {
                        val idTimemillis = System.currentTimeMillis().toInt()
                        if(typeVal == "One time"){
                            alarmReceiver.setOneTimeAlarm(this@ReminderAddUpdateActivity, AlarmReceiver.TYPE_ONE_TIME,
                                dateVal,
                                timeVal,
                                descVal,
                                idTimemillis)
                        }else if(typeVal == "Daily"){
                            alarmReceiver.setRepeatingAlarm(this@ReminderAddUpdateActivity, AlarmReceiver.TYPE_REPEATING,
                                timeVal,
                                descVal,
                                idTimemillis)
                        }else{
                            Log.e(TAG_REMINDER, "Reminder type not found!")
                        }

                        reminder.let { reminder ->
                            reminder?.id = idTimemillis
                            reminder?.time = timeVal
                            reminder?.description = descVal
                            reminder?.type = typeVal
                            reminder?.date = dateVal
                        }
                        if (isEdit) {
                            reminderAddUpdateViewModel.update(reminder as Reminder)
                        } else {
                            reminderAddUpdateViewModel.insert(reminder as Reminder)
                        }
                        finish()
                    }
                }
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
            textViewDate.isVisible = status
            textInputLayoutTitle2.isVisible = status
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
                    reminder?.let {
                        alarmReceiver.cancelAlarm(
                            this@ReminderAddUpdateActivity,
                            it.id)
                    }
                    reminderAddUpdateViewModel.delete(reminder as Reminder)
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