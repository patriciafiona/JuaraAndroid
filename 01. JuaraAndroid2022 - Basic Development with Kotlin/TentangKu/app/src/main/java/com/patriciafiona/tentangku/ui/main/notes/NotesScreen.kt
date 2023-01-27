package com.patriciafiona.tentangku.ui.main.notes

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
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.navigation.TentangkuScreen
import com.patriciafiona.tentangku.ui.main.ui.theme.Eunry
import com.patriciafiona.tentangku.ui.main.ui.theme.VeronaGreen
import com.patriciafiona.tentangku.ui.widgets.ItemNotes
import com.patriciafiona.tentangku.ui.widgets.Loader
import com.patriciafiona.tentangku.utils.Utils.OnLifecycleEvent

private lateinit var noteViewModel: NoteViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NotesScreen (navController: NavController, appCompatActivity: AppCompatActivity) {
    val lifecycleOwner = LocalLifecycleOwner.current

    val myNotesList = remember {
        mutableStateListOf<Note>()
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = VeronaGreen
    )

    val isLoading = remember{ mutableStateOf(true) }
    val notesAvailability = remember{ mutableStateOf(false) }

    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    OnLifecycle(
        myNotesList = myNotesList,
        lifecycleOwner = lifecycleOwner,
        appCompatActivity = appCompatActivity,
        isLoading = isLoading,
        notesAvailability = notesAvailability
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
                .background(VeronaGreen)
                .padding(top = 26.dp)
                .navigationBarsPadding()
                .fillMaxSize(),
            peekHeight = (LocalConfiguration.current.screenHeightDp * 0.5).dp,
            headerHeight = 60.dp,
            gesturesEnabled = true,
            appBar = {
                TopAppBar(
                    backgroundColor = VeronaGreen,
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
                        painter = painterResource(id = R.drawable.notes_bg),
                        contentDescription = "Notes Background",
                        contentScale = ContentScale.Crop
                    )
                }
            },
            frontLayerContent = {
                MainContent(
                    navController = navController,
                    backdropScaffoldState = backdropScaffoldState,
                    isLoading = isLoading,
                    notesAvailability = notesAvailability,
                    lifecycleOwner = lifecycleOwner,
                    myNotesList = myNotesList
                )
            }
        )
    }
}

@Composable
private fun OnLifecycle(
    myNotesList: SnapshotStateList<Note>,
    lifecycleOwner: LifecycleOwner,
    appCompatActivity: AppCompatActivity,
    isLoading: MutableState<Boolean>,
    notesAvailability: MutableState<Boolean>
) {
    OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                isLoading.value = false
                notesAvailability.value = false

                noteViewModel = obtainViewModel(appCompatActivity)

                myNotesList.clear()
                noteViewModel.getAllNotes().observe(lifecycleOwner) { noteList ->
                    if (noteList != null  && noteList.isNotEmpty()) {
                        val sortedNoteList = noteList.sortedByDescending { note -> note.id }
                        sortedNoteList.forEach{
                            myNotesList.add(it)
                        }
                        notesAvailability.value = true
                    }else{
                        notesAvailability.value = false
                    }
                }
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
    notesAvailability: MutableState<Boolean>,
    myNotesList: SnapshotStateList<Note>
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
                text = stringResource(id = R.string.notes),
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
                tint = VeronaGreen,
                contentDescription = "Expand icon"
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.note_history),
            style = TextStyle(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        )

        if(myNotesList.isNotEmpty()){
            LazyColumn (
                modifier = Modifier
                    .height(230.dp)
            ) {
                items(myNotesList.toList()) {data ->
                    ItemNotes(data, navController)
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
        
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.navigate(TentangkuScreen.NotesAddUpdateScreen.route)
                navController.currentBackStackEntry?.arguments?.putParcelable("notes", null)
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = VeronaGreen)
        ) {
            Text(
                text = stringResource(id = R.string.new_note),
                color = Color.White
            )
        }
    }
}

private fun obtainViewModel(activity: AppCompatActivity): NoteViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory)[NoteViewModel::class.java]
}