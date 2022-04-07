package com.patriciafiona.tentangku.ui.main.finance.addUpdate

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.databinding.ActivityFinanceAddUpdateBinding
import java.text.SimpleDateFormat
import java.util.*


class FinanceAddUpdateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinanceAddUpdateBinding

    val myCalendar: Calendar = Calendar.getInstance()

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

            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            val date =
                DatePickerDialog.OnDateSetListener { view, year, month, day ->
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
        }
    }

    private fun updateLabel() {
        val myFormat = "yyyy-MM-dd"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.edtTransactionDate.setText(dateFormat.format(myCalendar.time))
    }
}