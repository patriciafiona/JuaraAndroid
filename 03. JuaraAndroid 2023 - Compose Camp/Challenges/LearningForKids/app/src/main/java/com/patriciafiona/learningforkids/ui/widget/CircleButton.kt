package com.patriciafiona.learningforkids.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
    windowSize: WindowWidthSizeClass
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
            tint = btnIconColor,
            modifier = Modifier
                .fillMaxSize(if(windowSize == WindowWidthSizeClass.Compact) 1f else .7f)
                .padding(if(windowSize == WindowWidthSizeClass.Compact) 8.dp else 16.dp),
        )
    }
}