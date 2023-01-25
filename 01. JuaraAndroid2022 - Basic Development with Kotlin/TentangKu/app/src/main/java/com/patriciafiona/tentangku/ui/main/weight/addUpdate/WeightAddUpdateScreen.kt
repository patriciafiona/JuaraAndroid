package com.patriciafiona.tentangku.ui.main.weight.addUpdate

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
import android.widget.DatePicker
import android.widget.Space
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NoteAddUpdateActivity
import com.patriciafiona.tentangku.ui.main.ui.theme.Eunry
import com.patriciafiona.tentangku.ui.main.ui.theme.Green
import com.patriciafiona.tentangku.ui.main.ui.theme.WhiteSmoke
import com.patriciafiona.tentangku.ui.widgets.TextFieldDoubleTemplate
import com.patriciafiona.tentangku.ui.widgets.TextFieldStringTemplate
import com.patriciafiona.tentangku.utils.Utils.getCurrentDate
import java.util.*

private lateinit var weightAddUpdateViewModel: WeightAddUpdateViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WeightAddUpdateScreen (
    navController: NavController,
    weight: Weight?,
    appCompatActivity: AppCompatActivity
) {
    val context = LocalContext.current
    val openAlertDialog = remember { mutableStateOf(false)  }

    weightAddUpdateViewModel = obtainViewModel(appCompatActivity)

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val isEdit = remember { mutableStateOf(false) }

    val weightField = remember { mutableStateOf(
        weight?.value?.toDouble() ?: 0.0
    ) }
    val weightFieldError = remember { mutableStateOf(false) }
    val dateField = remember { mutableStateOf(
        weight?.date?.toString() ?: getCurrentDate(type = "date")
    ) }
    val dateFieldError = remember { mutableStateOf(false) }

    // Fetching the Local Context
    val mContext = LocalContext.current

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
        mContext,
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
    isEdit.value = weight != null

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
                elevation = 5.dp
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back icon",
                        tint = Color.Black
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.add_update_weight_bg),
                contentDescription = "Add Update weight image"
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                        text = if(isEdit.value) { stringResource(id = R.string.update_weight) } else{ stringResource(id = R.string.add_weight) },
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
                            text = stringResource(id = R.string.body_weight_kg),
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
                                        if (keyEvent.key == Key.Tab && keyEvent.nativeKeyEvent.action == ACTION_DOWN) {
                                            focusManager.moveFocus(FocusDirection.Down)
                                            true
                                        } else {
                                            false
                                        }
                                    },
                                mTitle = "Your Weight",
                                mValue = weightField,
                                keyboardActions = KeyboardActions(
                                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                                ),
                                isError = weightFieldError,
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number,
                                    imeAction = ImeAction.Next
                                )
                            )
                            if(weightFieldError.value) {
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
                                    modifier = Modifier.weight(1f),
                                    mTitle = "Date",
                                    mValue = dateField,
                                    keyboardActions = KeyboardActions(
                                        onDone = {keyboardController?.hide()}
                                    ),
                                    enable = false,
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Done
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
                                    text = if (isEdit.value) {
                                        stringResource(id = R.string.delete)
                                    } else {
                                        stringResource(id = R.string.cancel)
                                    },
                                    color = Color.White
                                )
                            }

                            Spacer(modifier = Modifier.width(5.dp))
                        }

                        Button(
                            onClick = {
                                val valueVal = weightField.value.toString().trim().toDouble()
                                val dateVal = dateField.value.trim()
                                when {
                                    valueVal.isNaN() || valueVal == 0.0 -> {
                                        weightFieldError.value = true
                                    }
                                    dateVal.isEmpty() -> {
                                        dateFieldError.value = true
                                    }
                                    else -> {
                                        var myWeight = Weight()
                                        myWeight = if(isEdit.value) {
                                            weight?.let { it1 ->
                                                Weight(
                                                    id = it1.id,
                                                    value = valueVal,
                                                    date = dateVal
                                                )
                                            }!!
                                        }else{
                                            Weight(
                                                value = valueVal,
                                                date = dateVal
                                            )
                                        }

                                        if (isEdit.value) {
                                            weightAddUpdateViewModel.update(myWeight as Weight)
                                            showToast(context.getString(R.string.changed), context)
                                        } else {
                                            weightAddUpdateViewModel.insert(myWeight as Weight)
                                            showToast(context.getString(R.string.added), context)
                                        }

                                        //back to main weight screen
                                        navController.navigateUp()
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Green)
                        ) {
                            Text(
                                text = if(isEdit.value) { stringResource(id = R.string.update_weight) } else{ stringResource(id = R.string.add_weight) },
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
                    weight = weight,
                    navController
                )
            }
        }
    }
}

@Composable
private fun ShowAlertDialog(
    context: Context,
    isEdit: MutableState<Boolean>,
    openAlertDialog: MutableState<Boolean>,
    weight: Weight?,
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
                        weightAddUpdateViewModel.delete(weight as Weight)
                        showToast(context.getString(R.string.deleted), context)

                        //back to weight main page
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

private fun obtainViewModel(activity: AppCompatActivity): WeightAddUpdateViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[WeightAddUpdateViewModel::class.java]
}