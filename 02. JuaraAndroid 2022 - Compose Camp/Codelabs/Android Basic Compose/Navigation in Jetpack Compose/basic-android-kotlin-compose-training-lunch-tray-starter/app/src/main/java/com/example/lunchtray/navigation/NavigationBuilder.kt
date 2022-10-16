package com.example.lunchtray.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lunchtray.datasource.DataSource
import com.example.lunchtray.ui.*

@Composable
fun NavigationBuilder() {
    val navController = rememberNavController()

    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    
    NavHost(navController = navController, startDestination = Screen.Start.name){
        composable(route = Screen.Start.name) {
            StartOrderScreen(
                onStartOrderButtonClicked = {
                    navController.navigate(Screen.EntreeMenu.name)
                }
            )
        }

        composable(route = Screen.EntreeMenu.name) {
            EntreeMenuScreen(
                options = DataSource.entreeMenuItems,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(Screen.Start.name, inclusive = false)
                },
                onNextButtonClicked = {
                    navController.navigate(Screen.SideDishMenu.name)
                },
                onSelectionChanged = { item ->
                    viewModel.updateEntree(item)
                }
            )
        }

        composable(route = Screen.SideDishMenu.name) {
            SideDishMenuScreen(
                options = DataSource.sideDishMenuItems,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(Screen.Start.name, inclusive = false)
                },
                onNextButtonClicked = {
                    navController.navigate(Screen.AccompanimentMenu.name)
                },
                onSelectionChanged = { item ->
                    viewModel.updateSideDish(item)
                }
            )
        }

        composable(route = Screen.AccompanimentMenu.name) {
            AccompanimentMenuScreen(
                options = DataSource.accompanimentMenuItems,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(Screen.Start.name, inclusive = false)
                },
                onNextButtonClicked = {
                    navController.navigate(Screen.Summary.name)
                },
                onSelectionChanged = { item ->
                    viewModel.updateAccompaniment(item)
                }
            )
        }

        composable(route = Screen.Summary.name) {
            CheckoutScreen(
                orderUiState = uiState,
                onCancelButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(Screen.Start.name, inclusive = false)
                },
                onNextButtonClicked = {
                    viewModel.resetOrder()
                    navController.popBackStack(Screen.Start.name, inclusive = false)
                }
            )
        }
    }
}