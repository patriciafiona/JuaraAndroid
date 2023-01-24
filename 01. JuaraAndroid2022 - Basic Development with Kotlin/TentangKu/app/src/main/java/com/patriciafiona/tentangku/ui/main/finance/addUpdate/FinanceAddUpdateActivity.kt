package com.patriciafiona.tentangku.ui.main.finance.addUpdate

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.utils.Utils
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.databinding.ActivityFinanceAddUpdateBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import java.text.SimpleDateFormat
import java.util.*


class FinanceAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinanceAddUpdateBinding

    val myCalendar: Calendar = Calendar.getInstance()

    companion object {
        const val EXTRA_FINANCE = "extra_finance"
    }
    private var isEdit = false
    private var transaction: FinanceTransaction? = null
    private lateinit var financeAddUpdateViewModel: FinanceAddUpdateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceAddUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            //set spinner dropdown
            val dropdown = edtTransactionType
            val items = resources.getStringArray(R.array.transaction_type)
            val adapter: ArrayAdapter<String> =
                ArrayAdapter<String>(
                    this@FinanceAddUpdateActivity,
                    android.R.layout.simple_spinner_dropdown_item,
                    items)
            dropdown.adapter = adapter

            financeAddUpdateViewModel = obtainViewModel(this@FinanceAddUpdateActivity)

            with(binding) {
                transaction = intent.getParcelableExtra(EXTRA_FINANCE)
                if (transaction != null) {
                    isEdit = true
                    btnAddUpdateTransaction.text = getString(R.string.update_transaction)
                    addUpdateFinanceTxt.text = getString(R.string.update_transaction)
                } else {
                    transaction = FinanceTransaction()
                    btnAddUpdateTransaction.text = getString(R.string.add_transaction)
                    addUpdateFinanceTxt.text = getString(R.string.add_transaction)
                }

                if (isEdit) {
                    if (transaction != null) {
                        transaction?.let { transaction ->
                            //set selected dropdown
                            if (transaction.type != null) {
                                val spinnerPosition = adapter.getPosition(transaction.type)
                                dropdown.setSelection(spinnerPosition)
                            }

                            edtNominalVal.setText(transaction.nominal.toString())
                            edtTransactionDate.setText(transaction.date)
                            edtDescription.setText(transaction.description.toString())
                            btnDelete.isVisible = true
                        }
                    }
                } else {
                    edtTransactionDate.setText(Utils.getCurrentDate("date"))
                    btnDelete.isVisible = false
                }
            }

            //Set button onclick
            val date =
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    myCalendar[Calendar.YEAR] = year
                    myCalendar[Calendar.MONTH] = month
                    myCalendar[Calendar.DAY_OF_MONTH] = day
                    updateLabel()
                }

            edtTransactionDate.setOnClickListener {
                val dialog = DatePickerDialog(
                    this@FinanceAddUpdateActivity,
                    date,
                    myCalendar[Calendar.YEAR],
                    myCalendar[Calendar.MONTH],
                    myCalendar[Calendar.DAY_OF_MONTH]
                )
                dialog.datePicker.maxDate = Date().time
                dialog.show()
            }

            btnAddUpdateTransaction.setOnClickListener {
                val valueVal = edtNominalVal.text.toString().trim().toDouble()
                val dateVal = edtTransactionDate.text.toString().trim()
                val descriptionVal = edtDescription.text.toString().trim()
                val typeVal = edtTransactionType.selectedItem.toString().trim()

                when {
                    valueVal.isNaN() || valueVal == 0.0 -> {
                        edtNominalVal.error = getString(R.string.empty)
                    }
                    dateVal.isEmpty() -> {
                        edtTransactionDate.error = getString(R.string.empty)
                    }
                    descriptionVal.isEmpty() -> {
                        edtDescription.error = getString(R.string.empty)
                    }

                    else -> {
                        transaction.let { transaction ->
                            transaction?.nominal = valueVal
                            transaction?.description = descriptionVal
                            transaction?.type = typeVal
                            transaction?.date = dateVal
                        }
                        if (isEdit) {
                            financeAddUpdateViewModel.update(transaction as FinanceTransaction)
                            showToast(getString(R.string.changed))
                        } else {
                            financeAddUpdateViewModel.insert(transaction as FinanceTransaction)
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
        binding.edtTransactionDate.setText(dateFormat.format(myCalendar.time))
    }

    private fun obtainViewModel(activity: AppCompatActivity): FinanceAddUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FinanceAddUpdateViewModel::class.java)
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
                    financeAddUpdateViewModel.delete(transaction as FinanceTransaction)
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