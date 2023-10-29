package com.patriciafiona.learningforkids.ui.theme.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    onCLickAction: () -> Unit,
    btnSize: Int,
    btnColor: Color,
    btnOutlineColor: Color,
    btnIcon: ImageVector,
    btnIconColor: Color,
) {
    IconButton(onClick = onCLickAction,
        modifier = Modifier
            .size(btnSize.dp)
            .clip(CircleShape)
            .background(btnColor)
            .border(3.dp, btnOutlineColor, shape = CircleShape)
    ) {
        Icon(
            btnIcon,
            contentDescription = "content description",
            tint = btnIconColor
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CircleButtonPreview(){
    CircleButton(
        onCLickAction = {},
        btnSize = 50,
        btnColor = Color.Green,
        btnOutlineColor = Color.DarkGray,
        btnIcon = Icons.Filled.Settings,
        btnIconColor = Color.White
    )
}