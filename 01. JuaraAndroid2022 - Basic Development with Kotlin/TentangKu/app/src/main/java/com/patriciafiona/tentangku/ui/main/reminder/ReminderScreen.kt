package com.patriciafiona.tentangku.ui.main.reminder

import android.content.Context
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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.Studio
import com.patriciafiona.tentangku.ui.widgets.ItemReminder
import com.patriciafiona.tentangku.ui.widgets.Loader
import com.patriciafiona.tentangku.utils.Utils.OnLifecycleEvent

private lateinit var reminderViewModel: ReminderViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReminderScreen (navController: NavController, appCompatActivity: AppCompatActivity) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current

    val reminderAvailability = remember {
        mutableStateOf(false)
    }
    val isLoading = remember{ mutableStateOf(true) }

    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )
    val myReminderList = remember {
        mutableStateListOf<Reminder>()
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Studio
    )

    OnLifecycle(
        myReminderList = myReminderList,
        lifecycleOwner = lifecycleOwner,
        appCompatActivity = appCompatActivity,
        isLoading = isLoading,
        reminderAvailability = reminderAvailability
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
                .background(Studio)
                .padding(top = 26.dp)
                .navigationBarsPadding()
                .fillMaxSize(),
            peekHeight = (LocalConfiguration.current.screenHeightDp * 0.5).dp,
            headerHeight = 60.dp,
            gesturesEnabled = true,
            appBar = {
                TopAppBar(
                    backgroundColor = Studio,
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
                        painter = painterResource(id = R.drawable.reminder_bg),
                        contentDescription = "Reminder Background",
                        contentScale = ContentScale.Crop
                    )
                }
            },
            frontLayerContent = {
                MainContent(
                    navController = navController,
                    backdropScaffoldState = backdropScaffoldState,
                    myReminderList = myReminderList,
                    context = context
                )
            }
        )
    }
}

@Composable
private fun OnLifecycle(
    myReminderList: SnapshotStateList<Reminder>,
    lifecycleOwner: LifecycleOwner,
    appCompatActivity: AppCompatActivity,
    isLoading: MutableState<Boolean>,
    reminderAvailability: MutableState<Boolean>
) {
    OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                init(
                    reminderAvailability = reminderAvailability,
                    lifecycleOwner = lifecycleOwner,
                    appCompatActivity = appCompatActivity,
                    myReminder = myReminderList,
                    isLoading = isLoading
                )
            }
            else -> { /* other stuff */
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun MainContent(
    navController: NavController,
    backdropScaffoldState: BackdropScaffoldState,
    myReminderList: SnapshotStateList<Reminder>,
    context: Context
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
                text = stringResource(id = R.string.reminder),
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
                tint = Studio,
                contentDescription = "Expand icon"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.reminder_history),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        if(myReminderList.isNotEmpty()){
            LazyColumn (
                modifier = Modifier
                    .height(230.dp)
            ) {
                items(myReminderList.toList()) {data ->
                    ItemReminder(
                        data = data,
                        navController = navController,
                        context = context,
                        reminderViewModel = reminderViewModel
                    )
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
                    color = Studio,
                    textAlign = TextAlign.Center
                )
            )

            Spacer(modifier = Modifier.height(50.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(TentangkuScreen.ReminderAddUpdateScreen.route)
                navController.currentBackStackEntry?.arguments?.putParcelable("reminder", null)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Studio)
        ) {
            Text(
                text = stringResource(id = R.string.new_reminder),
                color = Color.White
            )
        }
    }
}

private fun init(
    reminderAvailability: MutableState<Boolean>,
    lifecycleOwner: LifecycleOwner,
    appCompatActivity: AppCompatActivity,
    myReminder: SnapshotStateList<Reminder>,
    isLoading: MutableState<Boolean>
){
    reminderAvailability.value = false
    isLoading.value = true
    reminderViewModel = obtainViewModel(appCompatActivity)

    reminderViewModel.getAllReminder().observe(lifecycleOwner) { reminderList ->
        myReminder.clear()

        if (reminderList != null  && reminderList.isNotEmpty()) {
            val sortedList = reminderList.sortedByDescending { data -> data.date }
            sortedList.forEach {
                myReminder.add(it)
            }
            reminderAvailability.value = true
        }else{
            reminderAvailability.value = false
        }
        isLoading.value = false
    }
}

private fun obtainViewModel(activity: AppCompatActivity): ReminderViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[ReminderViewModel::class.java]
}