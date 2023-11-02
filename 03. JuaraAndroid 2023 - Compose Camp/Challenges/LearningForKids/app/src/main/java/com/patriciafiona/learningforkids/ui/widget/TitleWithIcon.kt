package com.patriciafiona.learningforkids.ui.widget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont

@Composable
fun TitleWithIcon(
    modifier: Modifier = Modifier,
    text: String,
    textSize: Int = 20,
    textColor: Color,
    iconImage: Int,
    isExpand: Boolean = false
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Image(
            painter = painterResource(id = iconImage),
            contentDescription = "Title Side icon",
            modifier = Modifier
                .size(if (isExpand) 90.dp else 45.dp)
        )
        Text(
            modifier = Modifier.weight(1f),
            text = text,
            style = TextStyle(
                fontFamily = PlayoutDemoFont,
                fontSize = if (isExpand) (textSize * 2).sp else textSize.sp,
                color = textColor,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
    }
}