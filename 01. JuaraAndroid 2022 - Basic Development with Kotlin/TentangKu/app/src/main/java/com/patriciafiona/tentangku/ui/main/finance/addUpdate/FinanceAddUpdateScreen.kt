package com.patriciafiona.tentangku.ui.main.finance.addUpdate

import android.app.DatePickerDialog
import android.content.Context
import android.view.KeyEvent
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.ui.theme.Boulder
import com.patriciafiona.tentangku.ui.main.ui.theme.Eunry
import com.patriciafiona.tentangku.ui.main.ui.theme.Green
import com.patriciafiona.tentangku.ui.main.ui.theme.WhiteSmoke
import com.patriciafiona.tentangku.ui.widgets.TextFieldDoubleTemplate
import com.patriciafiona.tentangku.ui.widgets.TextFieldStringTemplate
import com.patriciafiona.tentangku.utils.Utils
import java.util.*


private lateinit var financeAddUpdateViewModel: FinanceAddUpdateViewModel

@OptIn(ExperimentalComposeUiApi::class,
    ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun FinanceAddUpdateScreen(
    navController: NavController,
    finance: FinanceTransaction?,
    appCompatActivity: AppCompatActivity
){
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false)  }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(
        color = Boulder
    )

    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    financeAddUpdateViewModel = obtainViewModel(appCompatActivity)

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isEdit = remember { mutableStateOf(false) }

    val nominalField = remember { mutableStateOf(
        finance?.nominal ?: 0.0
    ) }
    val nominalFieldError = remember { mutableStateOf(false) }
    val dateField = remember { mutableStateOf(
        finance?.date ?: Utils.getCurrentDate(type = "date")
    ) }
    val dateFieldError = remember { mutableStateOf(false) }
    val descriptionField = remember { mutableStateOf(
        finance?.description ?: ""
    ) }
    val descriptionFieldError = remember { mutableStateOf(false) }

    //transaction type select-option
    val transactionTypes = listOf("Income", "Outcome")
    var optionExpanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf( finance?.type ?: transactionTypes[0]) }

    //Date picker section
    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDatePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            val monthInString = if(selectedMonth+1 < 10){
                "0${selectedMonth+1}"
            }else{
                (selectedMonth+1).toString()
            }
            val dayInString = if(selectedDayOfMonth < 10){
                "0$selectedDayOfMonth"
            }else{
                selectedDayOfMonth.toString()
            }
            dateField.value = "$selectedYear-$monthInString-$dayInString"
        }, mYear, mMonth, mDay
    )
    mDatePickerDialog.datePicker.maxDate = Date().time

    //check if edit or add new data
    isEdit.value = (finance != null)

    BackdropScaffold(
        scaffoldState = backdropScaffoldState,
        modifier = Modifier
            .background(Eunry)
            .padding(top = 26.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        peekHeight = (LocalConfiguration.current.screenHeightDp * 0.5).dp,
        headerHeight = 60.dp,
        gesturesEnabled = false,
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
                    painter = painterResource(id = R.drawable.finance_bg),
                    contentDescription = "finance Background",
                    contentScale = ContentScale.Crop
                )
            }
        },
        frontLayerContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                Card(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .background(WhiteSmoke),
                ) {
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = if(isEdit.value) { stringResource(id = R.string.update_transaction) } else{ stringResource(id = R.string.add_transaction) },
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )

                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = stringResource(id = R.string.transaction_type),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            )

                            ExposedDropdownMenuBox(
                                modifier = Modifier.weight(1.5f),
                                expanded = optionExpanded,
                                onExpandedChange = {
                                    optionExpanded = !optionExpanded
                                }
                            ) {
                                androidx.compose.material3.TextField(
                                    readOnly = true,
                                    value = selectedOptionText ,
                                    onValueChange = { },
                                    label = { Text(text = stringResource(id = R.string.transaction_type)) },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = optionExpanded
                                        )
                                    },
                                )
                                ExposedDropdownMenu(
                                    expanded = optionExpanded,
                                    onDismissRequest = {
                                        optionExpanded = false
                                    }
                                ) {
                                    transactionTypes.forEach { selectionOption ->
                                        DropdownMenuItem(
                                            onClick = {
                                                selectedOptionText = selectionOption
                                                optionExpanded = false
                                            }
                                        ) {
                                            Text(text = selectionOption)
                                        }
                                    }
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = stringResource(id = R.string.nominal),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            )
                            Column(
                                modifier = Modifier.weight(1.5f)
                            ) {
                                TextFieldDoubleTemplate(
                                    modifier = Modifier
                                        .onPreviewKeyEvent { keyEvent ->
                                            if (keyEvent.key == Key.Tab && keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                                                focusManager.moveFocus(FocusDirection.Down)
                                                true
                                            } else {
                                                false
                                            }
                                        },
                                    mTitle = stringResource(id = R.string.nominal),
                                    mValue = nominalField,
                                    keyboardActions = KeyboardActions(
                                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                    ),
                                    isError = nominalFieldError,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Number,
                                        imeAction = ImeAction.Next
                                    )
                                )
                                if(nominalFieldError.value) {
                                    Text(
                                        text = stringResource(id = R.string.please_fill_correct_value),
                                        color = Color.Red
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = stringResource(id = R.string.date),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            )
                            Column(
                                modifier = Modifier.weight(1.5f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    TextFieldStringTemplate(
                                        modifier = Modifier
                                            .weight(1f)
                                            .onPreviewKeyEvent { keyEvent ->
                                                if (keyEvent.key == Key.Tab && keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                                                    focusManager.moveFocus(FocusDirection.Down)
                                                    true
                                                } else {
                                                    false
                                                }
                                            },
                                        mTitle = stringResource(id = R.string.date),
                                        mValue = dateField,
                                        keyboardActions = KeyboardActions(
                                            onDone = {keyboardController?.hide()}
                                        ),
                                        enable = false,
                                        keyboardOptions = KeyboardOptions(
                                            keyboardType = KeyboardType.Text,
                                            imeAction = ImeAction.Next
                                        ),
                                        isError = dateFieldError
                                    )
                                    Spacer(modifier = Modifier.width(5.dp))
                                    Button(
                                        modifier = Modifier.width(45.dp),
                                        onClick = {
                                            mDatePickerDialog.show()
                                        },
                                        colors = ButtonDefaults.buttonColors(backgroundColor = Eunry)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.CalendarMonth,
                                            contentDescription = "Calendar icon"
                                        )
                                    }
                                }

                                if(dateFieldError.value) {
                                    Text(
                                        text = stringResource(id = R.string.please_fill_correct_value),
                                        color = Color.Red
                                    )
                                }
                            }
                        }

                        Row(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier
                                    .weight(1f),
                                text = stringResource(id = R.string.description),
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Gray
                                )
                            )
                            Column(
                                modifier = Modifier.weight(1.5f)
                            ) {
                                TextFieldStringTemplate(
                                    modifier = Modifier.height(100.dp),
                                    mTitle = stringResource(id = R.string.description),
                                    mValue = descriptionField,
                                    keyboardActions = KeyboardActions(
                                        onDone = {keyboardController?.hide()}
                                    ),
                                    isError = descriptionFieldError,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
                                    ),
                                    isSingleLine = false,
                                    maxLine = 10
                                )
                                if(descriptionFieldError.value) {
                                    Text(
                                        text = stringResource(id = R.string.please_fill_correct_value),
                                        color = Color.Red
                                    )
                                }
                            }
                        }

                        Row{
                            Spacer(modifier = Modifier.weight(1f))

                            if(isEdit.value) {
                                Button(
                                    onClick = {
                                        //cancel or delete data to database
                                        openAlertDialog.value = true
                                    },
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.delete),
                                        color = Color.White
                                    )
                                }

                                Spacer(modifier = Modifier.width(5.dp))
                            }

                            Button(
                                onClick = {
                                    val valueVal = nominalField.value.toString().trim().toDouble()
                                    val dateVal = dateField.value.trim()
                                    val descriptionVal = descriptionField.value.trim()
                                    when {
                                        valueVal.isNaN() || valueVal == 0.0 -> {
                                            nominalFieldError.value = true
                                        }
                                        dateVal.isEmpty() -> {
                                            dateFieldError.value = true
                                        }
                                        descriptionVal.isEmpty() -> {
                                            descriptionFieldError.value = true
                                        }
                                        else -> {
                                            val myFinance: FinanceTransaction = if(isEdit.value) {
                                                finance?.let { it1 ->
                                                    FinanceTransaction(
                                                        id = it1.id,
                                                        nominal = valueVal,
                                                        date = dateVal,
                                                        type = selectedOptionText,
                                                        description = descriptionVal
                                                    )
                                                }!!
                                            }else{
                                                FinanceTransaction(
                                                    nominal = valueVal,
                                                    date = dateVal,
                                                    type = selectedOptionText,
                                                    description = descriptionVal
                                                )
                                            }

                                            if (isEdit.value) {
                                                financeAddUpdateViewModel.update(myFinance as FinanceTransaction)
                                                showToast(context.getString(R.string.changed), context)
                                            } else {
                                                financeAddUpdateViewModel.insert(myFinance as FinanceTransaction)
                                                showToast(context.getString(R.string.added), context)
                                            }

                                            //back to main finance screen
                                            navController.navigateUp()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Green)
                            ) {
                                Text(
                                    text = if(isEdit.value) { stringResource(id = R.string.update_transaction) } else{ stringResource(id = R.string.add_transaction) },
                                    color = Color.White
                                )
                            }
                        }
                    }
                }

                if (openAlertDialog.value) {
                    ShowAlertDialog(
                        context = context,
                        isEdit,
                        openAlertDialog,
                        transaction = finance,
                        navController
                    )
                }
            }
        }
    )
}

@Composable
private fun ShowAlertDialog(
    context: Context,
    isEdit: MutableState<Boolean>,
    openAlertDialog: MutableState<Boolean>,
    transaction: FinanceTransaction?,
    navController: NavController
){
    AlertDialog(
        onDismissRequest = {
            openAlertDialog.value = false
        },
        title = {
            Text(
                text = if(isEdit.value){
                    stringResource(id = R.string.delete)
                }else{
                    stringResource(id = R.string.cancel)
                }
            )
        },
        text = {
            Text(
                text = if(isEdit.value){
                    stringResource(id = R.string.message_delete)
                }else{
                    stringResource(id = R.string.message_cancel)
                }
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    //Delete data
                    if (openAlertDialog.value) {
                        financeAddUpdateViewModel.delete(transaction as FinanceTransaction)
                        showToast(context.getString(R.string.deleted), context)

                        //back to finance main page
                        navController.navigateUp()
                    }
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Yellow)
            ) {
                Text(
                    text = stringResource(id = R.string.delete),
                    color = Color.Black
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    openAlertDialog.value = false
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(
                    stringResource(id = R.string.cancel),
                    color = Color.White
                )
            }
        }
    )
}

private fun showToast(message: String, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

private fun obtainViewModel(activity: AppCompatActivity): FinanceAddUpdateViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[FinanceAddUpdateViewModel::class.java]
}