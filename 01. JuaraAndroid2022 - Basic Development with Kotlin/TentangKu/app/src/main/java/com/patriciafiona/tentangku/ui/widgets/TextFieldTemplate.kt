package com.patriciafiona.tentangku.ui.widgets

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldStringTemplate(
    modifier: Modifier = Modifier,
    mTitle: String,
    mValue: MutableState<String>,
    enable: Boolean = true,
    isSingleLine: Boolean = true,
    maxLine: Int = 1,
    isError: MutableState<Boolean>,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions
){
    androidx.compose.material3.TextField(
        modifier = modifier,
        value = mValue.value,
        onValueChange = { data ->
            mValue.value = data
        },
        label = { Text(mTitle) },
        enabled = enable,
        isError = isError.value,
        singleLine = isSingleLine,
        maxLines = maxLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldDoubleTemplate(
    modifier: Modifier = Modifier,
    mTitle: String,
    mValue: MutableState<Double>,
    enable: Boolean = true,
    isSingleLine: Boolean = true,
    maxLine: Int = 1,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    isError: MutableState<Boolean>
){
    androidx.compose.material3.TextField(
        modifier = modifier,
        value = mValue.value.toString(),
        onValueChange = { data ->
            try {
                mValue.value = data.toDouble()
                isError.value = false
            }catch (e: java.lang.Exception) {
                isError.value = true
            }
        },
        label = { Text(mTitle) },
        enabled = enable,
        isError = isError.value,
        singleLine = isSingleLine,
        maxLines = maxLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextFieldStringNoBorderTemplate(
    modifier: Modifier = Modifier,
    mTitle: String,
    mValue: MutableState<String>,
    enable: Boolean = true,
    isSingleLine: Boolean = true,
    maxLine: Int = 1,
    isError: MutableState<Boolean>,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions,
    textStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        color = Color.Black
    )
){
    androidx.compose.material3.TextField(
        modifier = modifier,
        value = mValue.value,
        onValueChange = { data ->
            mValue.value = data
        },
        textStyle = textStyle,
        label = { Text(mTitle) },
        enabled = enable,
        isError = isError.value,
        singleLine = isSingleLine,
        maxLines = maxLine,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.White,
            textColor = Color.Gray,
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}