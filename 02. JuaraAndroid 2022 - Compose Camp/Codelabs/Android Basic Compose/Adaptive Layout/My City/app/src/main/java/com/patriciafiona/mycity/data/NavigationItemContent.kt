package com.patriciafiona.mycity.data

import androidx.compose.ui.graphics.vector.ImageVector

data class NavigationItemContent(
    val menuType: MenuType,
    val icon: ImageVector,
    val text: String
)

enum class MenuType {
    Museum, ShoppingCenter
}