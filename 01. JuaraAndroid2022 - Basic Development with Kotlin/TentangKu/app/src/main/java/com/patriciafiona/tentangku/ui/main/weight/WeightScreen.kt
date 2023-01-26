package com.patriciafiona.tentangku.ui.main.weight

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.AppleBlossom
import com.patriciafiona.tentangku.ui.main.ui.theme.Eunry
import com.patriciafiona.tentangku.ui.widgets.ItemWeight
import com.patriciafiona.tentangku.ui.widgets.Loader
import com.patriciafiona.tentangku.utils.Utils

private lateinit var weightProgressChart: LineChart
private var sortedFiveWeightList = ArrayList<Weight>()
private lateinit var sortedWeightListDesc: List<Weight>
private var listDate =  ArrayList<String>()

private lateinit var weightViewModel: WeightViewModel

@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeightScreen (navController: NavController, appCompatActivity: AppCompatActivity) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val myWeightList = remember {
        mutableStateListOf<Weight>()
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Eunry
    )

    val isLoading = remember{ mutableStateOf(true) }
    val weightAvailability = remember{ mutableStateOf(false) }

    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    OnLifecycle(
        appCompatActivity = appCompatActivity,
        isLoading = isLoading,
        weightAvailability = weightAvailability
    )

    if(isLoading.value){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.DarkGray.copy(alpha = .8f))
        ){
            Loader(
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }else{
        BackdropScaffold(
            scaffoldState = backdropScaffoldState,
            modifier = Modifier
                .background(Eunry)
                .padding(top = 26.dp)
                .navigationBarsPadding()
                .fillMaxSize(),
            peekHeight = (LocalConfiguration.current.screenHeightDp * 0.5).dp,
            headerHeight = 60.dp,
            gesturesEnabled = true,
            appBar = {
                TopAppBar(
                    backgroundColor = Eunry,
                    elevation = 0.dp
                ) {
                    IconButton(
                        onClick = {
                            navController.navigateUp()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back icon",
                            tint = Color.White
                        )
                    }
                }
            },
            backLayerContent = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.55f),
                        painter = painterResource(id = R.drawable.weigh_bg),
                        contentDescription = "Weight Background",
                        contentScale = ContentScale.Crop
                    )
                }
            },
            frontLayerContent = {
                MainContent(
                    navController = navController,
                    backdropScaffoldState = backdropScaffoldState,
                    isLoading = isLoading,
                    weightAvailability = weightAvailability,
                    lifecycleOwner = lifecycleOwner,
                    myWeightList = myWeightList
                )
            }
        )
    }
}

@Composable
private fun OnLifecycle(
    appCompatActivity: AppCompatActivity,
    isLoading: MutableState<Boolean>,
    weightAvailability: MutableState<Boolean>
) {
    Utils.OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                isLoading.value = false
                weightAvailability.value = false

                weightViewModel = obtainViewModel(appCompatActivity)
            }
            else -> { /* other stuff */ }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(
    navController: NavController,
    backdropScaffoldState: BackdropScaffoldState,
    lifecycleOwner: LifecycleOwner,
    isLoading: MutableState<Boolean>,
    weightAvailability: MutableState<Boolean>,
    myWeightList: SnapshotStateList<Weight>
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, end = 20.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
           Text(
               modifier = Modifier
                   .weight(1f),
               text = stringResource(id = R.string.my_weight),
               style = TextStyle(
                   fontSize = 16.sp,
                   fontWeight = FontWeight.Bold
               )
           )

            Icon(
                imageVector =
                if(backdropScaffoldState.isConcealed) {
                    Icons.Default.ArrowDropDown
                } else {
                    Icons.Default.ArrowDropUp
                } ,
                tint = Eunry,
                contentDescription = "Expand icon"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .weight(1f),
            text = stringResource(id = R.string.weight_progress),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                val view = LayoutInflater.from(context)
                    .inflate(R.layout.line_chart_weight, null, false)
                weightProgressChart = view.findViewById(R.id.weight_progress_chart)

                prepareChart(
                    lifecycleOwner = lifecycleOwner,
                    isLoading = isLoading,
                    weightAvailability = weightAvailability,
                    myWeightList = myWeightList
                )

                view // return the view
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    navController.navigate(TentangkuScreen.WeightAddUpdateScreen.route)
                    navController.currentBackStackEntry?.arguments?.putParcelable("weight", null)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Eunry)
            ) {
                Text(
                    text = stringResource(id = R.string.add_new_weight),
                    color = Color.White
                )
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.weight_history),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        if(myWeightList.isNotEmpty()){
            LazyColumn (
                modifier = Modifier
                    .height(300.dp)
            ) {
                items(myWeightList.toList()) {data ->
                    ItemWeight(data, navController)
                }
            }
        }else{
            Image(
                painter = painterResource(id = R.drawable.no_data),
                contentDescription = "No Data image",
                modifier = Modifier
                    .fillMaxWidth(.7f)
            )

            Text(
                text = stringResource(id = R.string.no_data),
                style = TextStyle(
                    fontSize = 14.sp,
                    color = Eunry,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

private fun prepareChart (
    lifecycleOwner: LifecycleOwner,
    isLoading: MutableState<Boolean>,
    weightAvailability: MutableState<Boolean>,
    myWeightList: SnapshotStateList<Weight>
) {
    //Prepare chart
    resetChart()

    //clean arraylist
    sortedWeightListDesc = ArrayList()
    sortedFiveWeightList = ArrayList()
    listDate = ArrayList()

    weightViewModel.getAllWeight().observe(lifecycleOwner) { weightList ->
        if (weightList != null  && weightList.isNotEmpty()) {
            sortedWeightListDesc = weightList.sortedByDescending { weight -> weight.date }
            sortedWeightListDesc.forEach {
                myWeightList.add(it)
            }

            if (sortedWeightListDesc.size >= 5) {
                for (i in 0..4) {
                    sortedFiveWeightList.add(sortedWeightListDesc[i])
                }
            }else{
                sortedFiveWeightList = ArrayList(sortedWeightListDesc)
            }

            sortedFiveWeightList = ArrayList(sortedFiveWeightList.sortedBy{ weight -> weight.date })

            for (dt in sortedFiveWeightList){
                listDate.add(dt.date.toString())
            }
            initLineChart()
            setDataToLineChart()

            isLoading.value = false
            weightAvailability.value = true
        }else{
            isLoading.value = false
            weightAvailability.value = false
        }
    }
}

private fun resetChart() {
    weightProgressChart.fitScreen()
    weightProgressChart.data?.clearValues()
    weightProgressChart.xAxis.valueFormatter = null
    weightProgressChart.notifyDataSetChanged()
    weightProgressChart.clear()
    weightProgressChart.invalidate()
}

private fun obtainViewModel(activity: AppCompatActivity): WeightViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[WeightViewModel::class.java]
}

private fun setDataToLineChart() {
    //now draw bar chart with dynamic data
    val entries: ArrayList<Entry> = ArrayList()
    if(sortedFiveWeightList.size > 0) {
        for (i in sortedFiveWeightList.indices) {
            val score = sortedFiveWeightList[i]
            entries.add(Entry(i.toFloat(), score.value?.toFloat() ?: 0f))
        }
    }

    val lineDataSet = LineDataSet(entries, "")

    val data = LineData(lineDataSet)
    data.setValueTextSize(10f)

    if (entries.isEmpty()){
        weightProgressChart.clear()
        weightProgressChart.data.clearValues()
    }else {
        weightProgressChart.data = data
    }

    data.notifyDataChanged()
    weightProgressChart.notifyDataSetChanged() // let the chart know it's data changed
    weightProgressChart.invalidate() // refresh
}

private fun initLineChart() {
    val xAxis: XAxis = weightProgressChart.xAxis

    //remove right y-axis
    weightProgressChart.axisRight.isEnabled = false

    //remove legend
    weightProgressChart.legend.isEnabled = false

    //remove description label
    weightProgressChart.description.isEnabled = false

    //add animation
    weightProgressChart.animateX(1000, Easing.EaseInSine)

    // to draw label on xAxis
    val formatter: ValueFormatter = object : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase): String {
            return if(value.toInt() >= 0 && value.toInt() < sortedFiveWeightList.size){
                listDate[value.toInt()]
            }else{
                " "
            }
        }
    }

    xAxis.position = XAxis.XAxisPosition.BOTTOM
    xAxis.valueFormatter = formatter
    xAxis.setDrawLabels(true)
    xAxis.granularity = 1f
    xAxis.labelRotationAngle = +90f
    xAxis.textSize = 12f
}