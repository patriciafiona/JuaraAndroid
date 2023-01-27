package com.patriciafiona.tentangku.ui.main.notes.addUpdate

import android.content.Context
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.factory.ViewModelFactory
import com.patriciafiona.tentangku.ui.widgets.TextFieldStringNoBorderTemplate
import com.patriciafiona.tentangku.utils.Utils.getCurrentDate

private lateinit var noteAddUpdateViewModel: NoteAddUpdateViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun NotesAddUpdateScreen(
    navController: NavController,
    notes: Note?,
    appCompatActivity: AppCompatActivity
){
    val context = LocalContext.current
    noteAddUpdateViewModel = obtainViewModel(appCompatActivity)

    val isEdit = remember { mutableStateOf(false) }
    //check if edit or add new data
    isEdit.value = (notes != null)

    val openAlertDialog = remember { mutableStateOf(false)  }

    val titleField = remember { mutableStateOf(
        notes?.title ?: "Title here"
    ) }
    val titleFieldError = remember { mutableStateOf(false) }
    val descriptionField = remember { mutableStateOf(
        notes?.description ?: ""
    ) }
    val descriptionFieldError = remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .background(Color.White)
            .padding(top = 26.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                backgroundColor = Color.White,
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
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                if (isEdit.value) {
                    IconButton(
                        onClick = {
                            //delete note
                            openAlertDialog.value = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete icon",
                            tint = Color.Black
                        )
                    }

                }

                IconButton(
                    onClick = {
                        //add or update note
                        val title = titleField.value.trim()
                        val description = descriptionField.value.trim()
                        when {
                            title.isEmpty() -> {
                                titleFieldError.value = true
                            }
                            description.isEmpty() -> {
                                descriptionFieldError.value = true
                            }
                            else -> {
                                if (isEdit.value) {
                                    val note: Note? = notes?.let {
                                        Note(
                                            id = it.id,
                                            title = title,
                                            description = description,
                                            date = it.date
                                        )
                                    }

                                    if (note != null) {
                                        noteAddUpdateViewModel.update(note)
                                        showToast(context.getString(R.string.changed), context)
                                    }
                                } else {
                                    val note = Note(
                                        title = title,
                                        description = description,
                                        date = getCurrentDate("date")
                                    )

                                    noteAddUpdateViewModel.insert(note)
                                    showToast(context.getString(R.string.added), context)
                                }

                                navController.navigateUp()
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Check icon",
                        tint = Color.Black
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            TextFieldStringNoBorderTemplate(
                modifier = Modifier
                    .fillMaxWidth()
                    .onPreviewKeyEvent { keyEvent ->
                        if (keyEvent.key == Key.Tab && keyEvent.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                            focusManager.moveFocus(FocusDirection.Down)
                            true
                        } else {
                            false
                        }
                    },
                mTitle = stringResource(id = R.string.input_title),
                mValue = titleField,
                keyboardActions = KeyboardActions(
                    onDone = {keyboardController?.hide()}
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                isError = titleFieldError,
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )

            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = getCurrentDate("date"),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            )

            TextFieldStringNoBorderTemplate(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
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
                maxLine = 10,
                textStyle = TextStyle(
                    color = Color.Black,
                    fontSize = 16.sp
                )
            )
            if(descriptionFieldError.value) {
                Text(
                    text = stringResource(id = R.string.please_fill_correct_value),
                    color = Color.Red
                )
            }

            if (openAlertDialog.value) {
                ShowAlertDialog(
                    context = context,
                    isEdit,
                    openAlertDialog,
                    note = notes,
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
    note: Note?,
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
                        noteAddUpdateViewModel.delete(note as Note)
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

private fun obtainViewModel(activity: AppCompatActivity): NoteAddUpdateViewModel {
    val factory = ViewModelFactory.getInstance(activity.application)
    return ViewModelProvider(activity, factory).get(NoteAddUpdateViewModel::class.java)
}