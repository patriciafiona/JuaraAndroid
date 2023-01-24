package com.patriciafiona.tentangku.ui.main.finance

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.patriciafiona.tentangku.utils.Utils
import com.patriciafiona.tentangku.databinding.ActivityFinanceBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.finance.addUpdate.FinanceAddUpdateActivity
import java.text.SimpleDateFormat
import java.util.*

class FinanceActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFinanceBinding
    private lateinit var adapter: FinanceAdapter

    private var total = 0.0
    private var transactionInMonth = 0
    private var thisMonthIncome = 0.0
    private var thisMonthOutcome = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinanceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    override fun onResume() {
        super.onResume()
        init()
    }

    @SuppressLint("SimpleDateFormat")
    private fun init(){
        with(binding){
            val financeViewModel = obtainViewModel(this@FinanceActivity)
            financeViewModel.getAllTransaction().observe(this@FinanceActivity) { transactionList ->
                clearDetails()
                if (transactionList != null  && transactionList.isNotEmpty()) {
                    val sortedTransactionList = transactionList.sortedByDescending { data -> data.date }
                    adapter.setListTransaction(sortedTransactionList)

                    //set details
                    for(transaction in transactionList){
                        //for filter current month transaction
                        val transactionDate = transaction.date?.let {
                            SimpleDateFormat("yyyy-MM-dd").parse(
                                it
                            )
                        }

                        //set the given date in one of the instance and current date in the other
                        if (transactionDate != null) {
                            val cal1: Calendar = Calendar.getInstance()
                            val cal2: Calendar = Calendar.getInstance()

                            cal1.time = transactionDate
                            cal2.time = Date()

                            //now compare the dates using methods on Calendar
                            if(cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
                                if(cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
                                    // the date falls in current month
                                    if (transaction.type.equals("Income")){
                                        thisMonthIncome += transaction.nominal!!
                                    }else if(transaction.type.equals("Outcome")){
                                        thisMonthOutcome += transaction.nominal!!
                                    }
                                    transactionInMonth+=1
                                }
                            }
                        }

                        if (transaction.type.equals("Income")){
                            total += transaction.nominal!!
                        }else if(transaction.type.equals("Outcome")){
                            total -= transaction.nominal!!
                        }
                    }

                    currentTotalBalance.text = Utils.setRupiahFormat(total)
                    currentTotalTransaction.text = transactionList.size.toString()
                    currentTotalTransactionInMonth.text = transactionInMonth.toString()
                    totalIncome.text = Utils.setRupiahFormat(thisMonthIncome)
                    totalSpendingMoney.text = Utils.setRupiahFormat(thisMonthOutcome)

                    checkTransactionsAvailability(true)
                }else{
                    checkTransactionsAvailability(false)
                }
            }

            adapter = FinanceAdapter(this@FinanceActivity)
            rvIncomeOutcome.layoutManager = LinearLayoutManager(this@FinanceActivity)
            rvIncomeOutcome.setHasFixedSize(true)
            rvIncomeOutcome.adapter = adapter

            btnBack.setOnClickListener {
                super.onBackPressed()
            }

            btnAddTransaction.setOnClickListener{
                val intent = Intent(this@FinanceActivity, FinanceAddUpdateActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun clearDetails(){
        total = 0.0
        transactionInMonth = 0
        thisMonthIncome = 0.0
        thisMonthOutcome = 0.0

        with(binding){
            currentTotalBalance.text = Utils.setRupiahFormat(total)
            currentTotalTransaction.text = "0"
            currentTotalTransactionInMonth.text = transactionInMonth.toString()
            totalIncome.text = Utils.setRupiahFormat(thisMonthIncome)
            totalSpendingMoney.text = Utils.setRupiahFormat(thisMonthOutcome)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FinanceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FinanceViewModel::class.java)
    }

    private fun checkTransactionsAvailability(status: Boolean){
        with(binding){
            rvIncomeOutcome.isVisible = status
            noFileImg.isVisible = !status
            noFileTxt.isVisible = !status
        }
    }
}