package com.patriciafiona.learningforkids.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.data.Alphabet
import com.patriciafiona.learningforkids.ui.theme.screen.alphabet_detail.AlphabetDetailScreen
import com.patriciafiona.learningforkids.ui.theme.screen.alphabet_list.AlphabetListScreen
import com.patriciafiona.learningforkids.ui.theme.screen.home.HomeScreen
import com.patriciafiona.learningforkids.ui.theme.screen.splash.SplashScreen
import com.patriciafiona.learningforkids.ui.theme.viewModel.AppViewModel

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()
    val viewModel = AppViewModel()
    val isMute = remember{ mutableStateOf(false) }

    NavHost(navController = navigationController, startDestination = AppScreen.SplashScreen.route) {

        composable(route = AppScreen.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }

        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController = navigationController, isMute = isMute)
        }

        composable(route = AppScreen.AlphabetListScreen.route) {
            AlphabetListScreen(navController = navigationController, viewModel, isMute)
        }

        composable(route = AppScreen.AlphabetDetailScreen.route) {
            AlphabetDetailScreen(
                viewModel = viewModel,
                navController = navigationController
            )
        }
    }
}