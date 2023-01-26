package com.patriciafiona.tentangku.ui.main.finance

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.*
import com.patriciafiona.tentangku.ui.main.weight.*
import com.patriciafiona.tentangku.ui.widgets.ItemFinance
import com.patriciafiona.tentangku.ui.widgets.Loader
import com.patriciafiona.tentangku.utils.Utils
import java.text.SimpleDateFormat
import java.util.*

private lateinit var total: MutableState<Double>
private lateinit var transactionInMonth: MutableState<Int>
private lateinit var thisMonthIncome: MutableState<Double>
private lateinit var thisMonthOutcome: MutableState<Double>

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FinanceScreen (navController: NavController, appCompatActivity: AppCompatActivity) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val myTransactionList = remember {
        mutableStateListOf<FinanceTransaction>()
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = ChathamsBlue
    )

    val isLoading = remember{ mutableStateOf(true) }

    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    total = remember{ mutableStateOf(0.0) }
    transactionInMonth = remember{ mutableStateOf(0) }
    thisMonthIncome = remember{ mutableStateOf(0.0) }
    thisMonthOutcome = remember{ mutableStateOf(0.0) }

    val transactionAvailability = remember { mutableStateOf(false) }

    OnLifecycle(
        appCompatActivity = appCompatActivity,
        isLoading = isLoading,
        lifecycleOwner = lifecycleOwner,
        myTransaction = myTransactionList,
        transactionAvailability = transactionAvailability
    )

    //View section
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
                .background(ChathamsBlue)
                .padding(top = 26.dp)
                .navigationBarsPadding()
                .fillMaxSize(),
            peekHeight = (LocalConfiguration.current.screenHeightDp * 0.5).dp,
            headerHeight = 60.dp,
            gesturesEnabled = true,
            appBar = {
                TopAppBar(
                    backgroundColor = ChathamsBlue,
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
                        .background(ChathamsBlue)
                        .fillMaxSize()
                ) {
                    Column (
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(bottom = 160.dp, start = 15.dp, end = 15.dp),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(
                                text = stringResource(id = R.string.balance),
                                style = TextStyle(
                                    color = CottonCandy,
                                    fontSize = 12.sp
                                )
                            )
                            Text(
                                text = Utils.setRupiahFormat(total.value),
                                style = TextStyle(
                                    color = Color.White,
                                    fontSize = 30.sp
                                )
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(.7f)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.total_transaction),
                                    style = TextStyle(
                                        color = CottonCandy,
                                        fontSize = 12.sp
                                    )
                                )
                                Text(
                                    text = myTransactionList.size.toString(),
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 24.sp
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Column(
                                modifier = Modifier
                                    .weight(1.3f)
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.total_transaction_in_month),
                                    style = TextStyle(
                                        color = CottonCandy,
                                        fontSize = 12.sp
                                    )
                                )
                                Text(
                                    text = transactionInMonth.value.toString(),
                                    style = TextStyle(
                                        color = Color.White,
                                        fontSize = 24.sp
                                    )
                                )
                            }
                        }
                    }
                }
            },
            frontLayerContent = {
                MainContent(
                    navController = navController,
                    backdropScaffoldState = backdropScaffoldState,
                    myTransactionList = myTransactionList
                )
            }
        )
    }
}

@Composable
private fun OnLifecycle(
    appCompatActivity: AppCompatActivity,
    lifecycleOwner:LifecycleOwner,
    transactionAvailability: MutableState<Boolean>,
    myTransaction: SnapshotStateList<FinanceTransaction>,
    isLoading: MutableState<Boolean>,
) {
    Utils.OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                isLoading.value = false
                PrepareFinanceData(
                    appCompatActivity = appCompatActivity,
                    lifecycleOwner = lifecycleOwner,
                    transactionAvailability = transactionAvailability,
                    myTransaction = myTransaction
                )
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
    myTransactionList: SnapshotStateList<FinanceTransaction>
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
                text = stringResource(id = R.string.summaries_this_month),
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
                tint = ChathamsBlue,
                contentDescription = "Expand icon"
            )
        }

        CardFinance(
            title = stringResource(id = R.string.spending_money),
            value = Utils.setRupiahFormat(thisMonthOutcome.value)
        )

        CardFinance(
            title = stringResource(id = R.string.income),
            value = Utils.setRupiahFormat(thisMonthIncome.value)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                text = stringResource(id = R.string.your_transaction),
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(8.dp))
            
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    //Go to add-update-delete finance screen
                    navController.navigate(TentangkuScreen.FinanceAddUpdateScreen.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = ChathamsBlue)
            ) {
                Row {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = stringResource(id = R.string.add_new_transaction),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.White
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Add new transaction icon",
                        tint = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        if(myTransactionList.isNotEmpty()){
            LazyColumn (
                modifier = Modifier
                    .height(300.dp)
            ) {
                items(myTransactionList.toList()) {data ->
                    ItemFinance(data = data, navController = navController)
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

@Composable
private fun CardFinance(
    title: String,
    value: String
) {
    androidx.compose.material3.Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = WhiteSmoke,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = ChathamsBlue
                ),
            )
            Text(
                modifier = Modifier.weight(1f),
                text = value,
                style = TextStyle(
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.End
                )
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
private fun PrepareFinanceData(
    appCompatActivity: AppCompatActivity,
    lifecycleOwner: LifecycleOwner,
    transactionAvailability: MutableState<Boolean>,
    myTransaction: SnapshotStateList<FinanceTransaction>
) {
    val financeViewModel = obtainViewModel(appCompatActivity)
    financeViewModel.getAllTransaction().observe(lifecycleOwner) { transactionList ->
        clearDetails()
        if (transactionList != null  && transactionList.isNotEmpty()) {
            val sortedTransactionList = transactionList.sortedByDescending { data -> data.date }
            myTransaction.clear()
            sortedTransactionList.forEach {
                myTransaction.add(it)
            }

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
                                thisMonthIncome.value += transaction.nominal!!
                            }else if(transaction.type.equals("Outcome")){
                                thisMonthOutcome.value += transaction.nominal!!
                            }
                            transactionInMonth.value += 1
                        }
                    }
                }

                if (transaction.type.equals("Income")){
                    total.value += transaction.nominal!!
                }else if(transaction.type.equals("Outcome")){
                    total.value -= transaction.nominal!!
                }
            }

            transactionAvailability.value = true
        }else{
            transactionAvailability.value = false
        }
    }
}

private fun clearDetails(){
    total.value = 0.0
    transactionInMonth.value = 0
    thisMonthIncome.value = 0.0
    thisMonthOutcome.value = 0.0
}

private fun obtainViewModel(activity: AppCompatActivity): FinanceViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[FinanceViewModel::class.java]
}