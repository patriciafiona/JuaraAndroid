package com.patriciafiona.tentangku.ui.main.home

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Menu
import com.patriciafiona.tentangku.databinding.FragmentHomeBinding
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.finance.FinanceActivity
import com.patriciafiona.tentangku.ui.main.finance.FinanceViewModel
import com.patriciafiona.tentangku.ui.main.notes.NotesActivity
import com.patriciafiona.tentangku.ui.main.reminder.ReminderActivity
import com.patriciafiona.tentangku.ui.main.weather.WeatherActivity
import com.patriciafiona.tentangku.ui.main.weight.WeightActivity
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding
    private lateinit var rvMenu: RecyclerView
    private val list = ArrayList<Menu>()

    private var valueList= ArrayList<Double>()
    private var thisMonthIncome: Double = 0.0
    private var thisMonthOutcome: Double = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun init(){
        list.clear()
        with(binding){
            val user = Firebase.auth.currentUser
            if (user != null) {
                userName.text = user.displayName
            }
            rvMenu = rvMainMenu
            rvMenu.setHasFixedSize(true)

            list.addAll(listMenus)
            showRecyclerList()

            btnFinanceDetail.setOnClickListener {
                val intent = Intent(requireActivity(), FinanceActivity::class.java)
                startActivity(intent)
            }

            val financeViewModel = obtainFinanceViewModel(requireActivity() as AppCompatActivity)
            financeViewModel.getAllTransaction().observe(requireActivity()) { transactionList ->
                resetChart()
                valueList.clear()
                thisMonthIncome = 0.0
                thisMonthOutcome = 0.0

                if (transactionList != null  && transactionList.isNotEmpty()) {
                    chartDetail.isVisible = true

                    for(transaction in transactionList) {
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
                                }
                            }
                        }
                    }

                    valueList.add(thisMonthIncome)
                    valueList.add(thisMonthOutcome)

                    //fit the data into a bar
                    val entries: ArrayList<BarEntry> = ArrayList()
                    if(valueList.size > 0) {
                        for (i in valueList.indices) {
                            val score = valueList[i]
                            entries.add(BarEntry(i.toFloat(), score.toFloat()))
                        }
                    }

                    val barDataSet = BarDataSet(entries, "This month history")
                    barDataSet.colors = arrayListOf(
                        Color.GREEN,
                        Color.RED,
                    )
                    val data = BarData(barDataSet)

                    if (entries.isEmpty()){
                        financeBalanceChart.clear()
                        financeBalanceChart.data.clearValues()
                    }else {
                        financeBalanceChart.data = data
                    }

                    data.notifyDataChanged()
                    data.setValueTextSize(10f)
                    financeBalanceChart.notifyDataSetChanged()
                    financeBalanceChart.invalidate()

                    //init bar chart
                    val listX = arrayListOf("Income", "Outcome")
//                    val formatter: ValueFormatter = object : ValueFormatter() {
//                        override fun getAxisLabel(value: Float, axis: AxisBase): String {
//                            return if(value.toInt() >= 0 && value.toInt() < valueList.size){
//                                listX[value.toInt()]
//                            }else{
//                                " "
//                            }
//                        }
//                    }

                    //remove legend
                    financeBalanceChart.legend.isEnabled = false

                    //remove description label
                    financeBalanceChart.description.isEnabled = false

                    //add animation
                    financeBalanceChart.animateX(1000, Easing.EaseInSine)

                    val xAxis: XAxis = financeBalanceChart.xAxis
                    xAxis.position = XAxis.XAxisPosition.BOTTOM
                    //xAxis.valueFormatter = formatter
                    xAxis.setDrawLabels(true)
                    xAxis.granularity = 1f
                    xAxis.textSize = 12f
                }else{
                    chartDetail.isVisible = false
                }
            }
        }
    }

    private fun resetChart() {
        with(binding){
            financeBalanceChart.fitScreen()
            financeBalanceChart.data?.clearValues()
            financeBalanceChart.xAxis.valueFormatter = null
            financeBalanceChart.notifyDataSetChanged()
            financeBalanceChart.clear()
            financeBalanceChart.invalidate()
        }
    }

    private val listMenus: ArrayList<Menu>
        @SuppressLint("Recycle")
        get() {
            val dataName = resources.getStringArray(R.array.menu_name)
            val dataPhoto = resources.obtainTypedArray(R.array.menu_img)
            val listMenu = ArrayList<Menu>()
            for (i in dataName.indices) {
                val menu = Menu(dataName[i], dataPhoto.getResourceId(i, -1))
                listMenu.add(menu)
            }
            return listMenu
        }
    private fun showRecyclerList() {
        rvMenu.layoutManager = GridLayoutManager(requireActivity(), 5)
        val menuAdapter = MenuAdapter(list)
        rvMenu.adapter = menuAdapter

        menuAdapter.setOnItemClickCallback(object : MenuAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Menu) {
                showSelectedHero(data)
            }
        })
    }

    private fun showSelectedHero(menu: Menu) {
        when(menu.name){
            "Notes" -> {
                val intent = Intent(requireActivity(), NotesActivity::class.java)
                startActivity(intent)
            }
            "Body Weight" -> {
                val intent = Intent(requireActivity(), WeightActivity::class.java)
                startActivity(intent)
            }
            "Finance" -> {
                val intent = Intent(requireActivity(), FinanceActivity::class.java)
                startActivity(intent)
            }
            "Reminder" -> {
                val intent = Intent(requireActivity(), ReminderActivity::class.java)
                startActivity(intent)
            }
            "Weather" -> {
                val intent = Intent(requireActivity(), WeatherActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun obtainFinanceViewModel(activity: AppCompatActivity): FinanceViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FinanceViewModel::class.java)
    }

    override fun onResume() {
        super.onResume()
        init()
    }

}