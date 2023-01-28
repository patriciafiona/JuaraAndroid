package com.patriciafiona.tentangku.ui.main.home

import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Color.GREEN
import android.graphics.Color.RED
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextClock
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Menu
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.helper.BackPress
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.finance.FinanceViewModel
import com.patriciafiona.tentangku.ui.main.ui.theme.Boulder
import com.patriciafiona.tentangku.ui.main.ui.theme.Sepia
import com.patriciafiona.tentangku.ui.main.ui.theme.WhiteSmoke
import com.patriciafiona.tentangku.ui.widgets.DrawerContent
import com.patriciafiona.tentangku.ui.widgets.LocationPermission
import com.patriciafiona.tentangku.utils.Utils.OnLifecycleEvent
import com.patriciafiona.tentangku.utils.Utils.getTimeGreetingStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

private lateinit var mAuth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var googleSignInClient: GoogleSignInClient

//Finance bar
private lateinit var valueList: SnapshotStateList<Double>
private lateinit var thisMonthIncome: MutableState<Double>
private lateinit var thisMonthOutcome: MutableState<Double>
private lateinit var userFirebase: FirebaseUser

private lateinit var financeBalanceChart: BarChart

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    appCompatActivity: AppCompatActivity
){
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Boulder
    )

    thisMonthIncome = remember{ mutableStateOf(0.0) }
    thisMonthOutcome = remember{ mutableStateOf(0.0) }
    valueList = remember {
        mutableStateListOf()
    }

    val chartDetailVisibility = remember{ mutableStateOf(false) }

    OnLifecycle(
        appCompatActivity = appCompatActivity,
        chartDetailVisibility = chartDetailVisibility,
        lifecycleOwner = lifecycleOwner
    )

    //Back press exit attributes
    var showToast by remember { mutableStateOf(false) }

    var backPressState by remember { mutableStateOf<BackPress>(BackPress.Idle) }

    if(showToast){
        Toast.makeText(context, "Press again to exit", Toast.LENGTH_SHORT).show()
        showToast= false
    }


    LaunchedEffect(key1 = backPressState) {
        if (backPressState == BackPress.InitialTouch) {
            delay(2000)
            backPressState = BackPress.Idle
        }
    }

    BackHandler(backPressState == BackPress.Idle) {
        backPressState = BackPress.InitialTouch
        showToast = true
    }

    //Prepare for Google Account info
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.default_web_client_id))
        .requestEmail()
        .build()

    googleSignInClient = GoogleSignIn.getClient(context, gso)

    val user = Firebase.auth.currentUser
    if (user != null) {
        // User is signed in
        mAuth = FirebaseAuth.getInstance()
        userFirebase = Firebase.auth.currentUser!!
    }


    //Prepare for location detection
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    // Home menu data preparation
    val dataName = stringArrayResource(R.array.menu_name)
    val imageRes = arrayOf(
        R.drawable.weight_icon,
        R.drawable.finance_icon,
        R.drawable.notes_icon,
        R.drawable.reminder_icon,
        R.drawable.weather_icon
    )
    val listMenu = ArrayList<Menu>()
    for (i in dataName.indices) {
        val menu = Menu(dataName[i], imageRes[i])
        listMenu.add(menu)
    }

    //View Section
    if (locationPermissionsState.allPermissionsGranted) {
        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier
                .statusBarsPadding()
                .navigationBarsPadding()
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    backgroundColor = WhiteSmoke,
                    elevation = 0.dp
                ) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu icon",
                            tint = Color.LightGray
                        )
                    }
                }
            },
            drawerGesturesEnabled = true,
            drawerContent = {
                DrawerContent(
                    navController,
                    coroutineScope,
                    scaffoldState,
                    mAuth = mAuth,
                    googleSignInClient = googleSignInClient,
                    userFirebase = userFirebase
                )
            },
            drawerBackgroundColor = Color.White
        ) {
            BackdropScaffold(
                scaffoldState = backdropScaffoldState,
                modifier = Modifier
                    .padding(it),
                appBar = {},
                backLayerContent = {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.home_bg),
                                contentDescription = "Home Background",
                                contentScale = ContentScale.Crop
                            )
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.wave_gray),
                                contentDescription = "Wave",
                                contentScale = ContentScale.Crop
                            )
                        }

                        Box (
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(bottom = 160.dp, start = 20.dp, end = 20.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                 horizontalArrangement = Arrangement.Center
                            ) {
                                Card(
                                    modifier = Modifier
                                        .weight(1.3f),
                                    elevation = 5.dp,
                                ) {
                                    Column (
                                        modifier = Modifier
                                            .padding(8.dp),
                                    ) {
                                        Text(
                                            getTimeGreetingStatus(),
                                            style = TextStyle(
                                                color = Sepia,
                                                fontSize = 14.sp
                                            )
                                        )
                                        if (user != null) {
                                            user.displayName?.let { it1 ->
                                                Text(
                                                    it1,
                                                    style = TextStyle(
                                                        color = Sepia,
                                                        fontSize = 18.sp,
                                                        fontWeight = FontWeight.Bold
                                                    ),
                                                    maxLines = 1,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                        }else{
                                            Text(
                                                "Unknown username",
                                                style = TextStyle(
                                                    color = Sepia,
                                                    fontSize = 18.sp,
                                                    fontWeight = FontWeight.Bold,
                                                ),
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                Card(
                                    modifier = Modifier
                                        .weight(.7f),
                                    elevation = 5.dp
                                ) {
                                    AndroidView(
                                        // on below line we are initializing our text clock.
                                        factory = { context ->
                                            TextClock(context).apply {
                                                // on below line we are setting 12 hour format.
                                                format12Hour?.let { this.format12Hour = "hh:mm a" }
                                                // on below line we are setting time zone.
                                                timeZone?.let { timeZone -> this.timeZone = timeZone }
                                                // on below line we are setting text size.
                                                textSize.let { this.textSize = 18f }
                                                gravity.let { this.gravity = Gravity.CENTER }
                                                setTextColor(ContextCompat.getColor(context, R.color.sepia))
                                            }
                                        },
                                        // on below line we are adding padding.
                                        modifier = Modifier.padding(5.dp),
                                    )
                                }
                            }
                        }
                    }
                },
                frontLayerContent = {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .fillMaxSize()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(id = R.string.menu),
                            style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Card(
                            elevation = 5.dp,
                            modifier = Modifier
                                .height(220.dp)
                        ) {
                            LazyVerticalGrid(
                                modifier = Modifier
                                    .padding(12.dp)
                                    .background(Color.White),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                columns = GridCells.Adaptive(minSize = 70.dp)
                            ) {
                                items(listMenu) { menu ->
                                    Column(
                                        modifier = Modifier
                                            .padding(5.dp)
                                            .weight(1f),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Card(
                                            shape = RoundedCornerShape(10.dp),
                                            modifier = Modifier
                                                .size(50.dp)
                                                .clickable {
                                                    when (menu.name) {
                                                        dataName[0] -> {
                                                            // Body weight
                                                            navController.navigate(TentangkuScreen.WeightScreen.route)
                                                        }
                                                        dataName[1] -> {
                                                            //Finance
                                                            navController.navigate(TentangkuScreen.FinanceScreen.route)
                                                        }
                                                        dataName[2] -> {
                                                            //Notes
                                                            navController.navigate(TentangkuScreen.NotesScreen.route)
                                                        }
                                                        dataName[3] -> {
                                                            //Reminder
                                                            navController.navigate(TentangkuScreen.ReminderScreen.route)
                                                        }
                                                        dataName[4] -> {
                                                            //Weather
                                                            navController.navigate(TentangkuScreen.WeatherScreen.route)
                                                        }
                                                    }
                                                },
                                        ) {
                                            Image(
                                                painter = painterResource(id = menu.icon),
                                                contentDescription = "Menu icon",
                                                contentScale = ContentScale.Crop
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(10.dp))
                                        Text(
                                            text = menu.name,
                                            style = TextStyle(
                                                color = Color.Black,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight.Bold
                                            ),
                                            textAlign = TextAlign.Center
                                        )
                                    }
                                }
                            }
                        }

                        Row(modifier = Modifier.padding(top = 20.dp)) {
                            Text(
                                modifier = Modifier
                                    .weight(.8f),
                                text = stringResource(id = R.string.my_finance),
                                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                            )
                            IconButton(
                                onClick = {
                                    navController.navigate(TentangkuScreen.FinanceScreen.route)
                                },
                                modifier = Modifier.size(30.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowForwardIos,
                                    contentDescription = "My Finance button",
                                )
                            }
                        }

                        AndroidView(
                            modifier = Modifier.fillMaxWidth(),
                            factory = { context ->
                                val view = LayoutInflater.from(context)
                                    .inflate(R.layout.bar_chart_finance, null, false)
                                financeBalanceChart = view.findViewById(R.id.finance_balance_chart)
                                view // return the view
                            }
                        )

                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = stringResource(id = R.string.this_is_your_finance_summary_in_this_month),
                            style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Center
                        )

                    }
                },
                peekHeight = (LocalConfiguration.current.screenHeightDp * 0.4).dp,
                headerHeight = 60.dp,
                gesturesEnabled = false
            )
        }
    }else {
        LocationPermission(
            locationPermissionsState
        )
    }
}

private fun resetChart() {
    if(::financeBalanceChart.isInitialized) {
        financeBalanceChart.fitScreen()
        financeBalanceChart.data?.clearValues()
        financeBalanceChart.xAxis.valueFormatter = null
        financeBalanceChart.notifyDataSetChanged()
        financeBalanceChart.clear()
        financeBalanceChart.invalidate()
    }
}

@SuppressLint("SimpleDateFormat")
private fun prepareFinanceData(
    lifecycleOwner: LifecycleOwner,
    appCompatActivity: AppCompatActivity,
    chartDetailVisibility: MutableState<Boolean>
){
    val financeViewModel = obtainFinanceViewModel(appCompatActivity)

    financeViewModel.getAllTransaction().observe(lifecycleOwner) { transactionList ->
        resetChart()
        valueList.clear()
        thisMonthIncome.value = 0.0
        thisMonthOutcome.value = 0.0

        if (transactionList != null  && transactionList.isNotEmpty()) {
            chartDetailVisibility.value = true

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
                                thisMonthIncome.value += transaction.nominal!!
                            }else if(transaction.type.equals("Outcome")){
                                thisMonthOutcome.value += transaction.nominal!!
                            }
                        }
                    }
                }
            }

            valueList.add(thisMonthIncome.value)
            valueList.add(thisMonthOutcome.value)

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
                GREEN,
                RED,
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
//            val listX = arrayListOf("Income", "Outcome")
//            val formatter: ValueFormatter = object : ValueFormatter() {
//                override fun getAxisLabel(value: Float, axis: AxisBase): String {
//                    return if(value.toInt() >= 0 && value.toInt() < valueList.size){
//                        listX[value.toInt()]
//                    }else{
//                        " "
//                    }
//                }
//            }

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
            chartDetailVisibility.value = false
        }
    }
}

@Composable
private fun OnLifecycle(
    appCompatActivity: AppCompatActivity,
    lifecycleOwner:LifecycleOwner,
    chartDetailVisibility: MutableState<Boolean>
) {
    OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                prepareFinanceData(
                    lifecycleOwner = lifecycleOwner,
                    appCompatActivity = appCompatActivity,
                    chartDetailVisibility = chartDetailVisibility
                )
            }
            else -> { /* other stuff */ }
        }
    }
}

private fun obtainFinanceViewModel(activity: AppCompatActivity): FinanceViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[FinanceViewModel::class.java]
}
