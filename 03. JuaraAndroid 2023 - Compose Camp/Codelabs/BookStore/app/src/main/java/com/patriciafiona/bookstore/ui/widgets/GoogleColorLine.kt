package com.patriciafiona.bookstore.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patriciafiona.bookstore.ui.theme.GoogleBlue
import com.patriciafiona.bookstore.ui.theme.GoogleGreen
import com.patriciafiona.bookstore.ui.theme.GoogleRed
import com.patriciafiona.bookstore.ui.theme.GoogleYellow

@Composable
fun GoogleColorLine() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GoogleBlue)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GoogleRed)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GoogleYellow)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GoogleBlue)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GoogleGreen)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(GoogleRed)
        )
    }
}