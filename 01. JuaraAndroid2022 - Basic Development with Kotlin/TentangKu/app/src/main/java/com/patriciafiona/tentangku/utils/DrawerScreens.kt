package com.patriciafiona.tentangku.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerScreens(val title: String, val icon: ImageVector) {
    object About : DrawerScreens("About", Icons.Default.Info)
    object SignOut : DrawerScreens("Sign Out", Icons.Default.Lock)
}