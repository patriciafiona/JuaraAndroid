package com.patriciafiona.tentangku.ui.main.weight.addUpdate

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.Utils
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.databinding.ActivityWeightAddUpdateBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import java.text.SimpleDateFormat
import java.util.*


class WeightAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeightAddUpdateBinding
    val myCalendar: Calendar = Calendar.getInstance()

    companion object {
        const val EXTRA_WEIGHT = "extra_weight"
    }
    private var isEdit = false
    private var weight: Weight? = null
    private lateinit var weightAddUpdateViewModel: WeightAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeightAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        weightAddUpdateViewModel = obtainViewModel(this)

        with(binding){
            weight = intent.getParcelableExtra(WeightAddUpdateActivity.EXTRA_WEIGHT)
            if (weight != null) {
                isEdit = true
                btnAddUpdateWeight.text = getString(R.string.update_weight)
                addUpdateWeightTxt.text = getString(R.string.update_weight)
            } else {
                weight = Weight()
                btnAddUpdateWeight.text = getString(R.string.add_weight)
                addUpdateWeightTxt.text = getString(R.string.add_weight)
            }

            if (isEdit) {
                if (weight != null) {
                    weight?.let { weight ->
                        edtWeightVal.setText(weight.value.toString())
                        edtWeightDate.setText(weight.date)
                        btnDelete.isVisible = true
                    }
                }
            }else{
                edtWeightDate.setText(Utils.getCurrentDate("date"))
                btnDelete.isVisible = false
            }

            val date =
                OnDateSetListener { _, year, month, day ->
                    myCalendar[Calendar.YEAR] = year
                    myCalendar[Calendar.MONTH] = month
                    myCalendar[Calendar.DAY_OF_MONTH] = day
                    updateLabel()
                }

            edtWeightDate.setOnClickListener {
                val dialog = DatePickerDialog(
                    this@WeightAddUpdateActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )
                dialog.datePicker.maxDate = Date().time
                dialog.show()
            }

            btnAddUpdateWeight.setOnClickListener {
                val valueVal = edtWeightVal.text.toString().trim().toDouble()
                val dateVal = edtWeightDate.text.toString().trim()
                when {
                    valueVal.isNaN() || valueVal == 0.0 -> {
                        edtWeightVal.error = getString(R.string.empty)
                    }
                    dateVal.isEmpty() -> {
                        edtWeightDate.error = getString(R.string.empty)
                    }
                    else -> {
                        weight.let { weight ->
                            weight?.value = valueVal
                            weight?.date = dateVal
                        }
                        if (isEdit) {
                            weightAddUpdateViewModel.update(weight as Weight)
                            showToast(getString(R.string.changed))
                        } else {
                            weightAddUpdateViewModel.insert(weight as Weight)
                            showToast(getString(R.string.added))
                        }
                        finish()
                    }
                }
            }

            btnBack.setOnClickListener {
                showAlertDialog(NoteAddUpdateActivity.ALERT_DIALOG_CLOSE)
            }

            btnDelete.setOnClickListener {
                showAlertDialog(NoteAddUpdateActivity.ALERT_DIALOG_DELETE)
            }
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtWeightDate.setText(dateFormat.format(myCalendar.time))
    }

    private fun obtainViewModel(activity: AppCompatActivity): WeightAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(WeightAddUpdateViewModel::class.java)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == NoteAddUpdateActivity.ALERT_DIALOG_CLOSE
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
                    weightAddUpdateViewModel.delete(weight as Weight)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
        showAlertDialog(NoteAddUpdateActivity.ALERT_DIALOG_CLOSE)
    }
}