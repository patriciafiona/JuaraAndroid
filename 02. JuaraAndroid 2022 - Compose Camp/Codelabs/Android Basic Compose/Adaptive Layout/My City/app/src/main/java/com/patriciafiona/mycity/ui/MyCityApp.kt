package com.patriciafiona.mycity.ui

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patriciafiona.mycity.data.MenuType
import com.patriciafiona.mycity.data.model.Data
import com.patriciafiona.mycity.ui.utils.ContentType
import com.patriciafiona.mycity.ui.utils.NavigationType

@Composable
fun MyCityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
){
    val navigationType: NavigationType
    val contentType: ContentType
    val viewModel: MainViewModel = viewModel()
    val uiState = viewModel.uiState.collectAsState().value

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = NavigationType.PERMANENT_NAVIGATION_DRAWER
            contentType = ContentType.LIST_AND_DETAIL
        }
        else -> {
            navigationType = NavigationType.BOTTOM_NAVIGATION
            contentType = ContentType.LIST_ONLY
        }
    }
    HomeScreen(
        navigationType = navigationType,
        contentType = contentType,
        uiState = uiState,
        onTabPressed = { menuType: MenuType ->
            viewModel.updateCurrentMenu(menuType = menuType)
            viewModel.resetHomeScreenStates()
        },
        onCardPressed = { data: Data ->
            viewModel.updateDetailsScreenStates(
                data = data
            )
        },
        onDetailScreenBackPressed = {
            viewModel.resetHomeScreenStates()
        },
        modifier = modifier
    )
}