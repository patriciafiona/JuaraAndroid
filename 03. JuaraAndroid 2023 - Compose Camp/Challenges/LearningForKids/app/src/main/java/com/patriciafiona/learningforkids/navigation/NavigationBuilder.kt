package com.patriciafiona.learningforkids.navigation

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.ui.theme.screen.alphabet_detail.AlphabetDetailScreen
import com.patriciafiona.learningforkids.ui.theme.screen.alphabet_list.AlphabetListScreen
import com.patriciafiona.learningforkids.ui.theme.screen.color_detail.ColorDetailScreen
import com.patriciafiona.learningforkids.ui.theme.screen.color_list.ColorListScreen
import com.patriciafiona.learningforkids.ui.theme.screen.home.HomeScreen
import com.patriciafiona.learningforkids.ui.theme.screen.splash.SplashScreen
import com.patriciafiona.learningforkids.ui.theme.viewModel.AppViewModel

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()
    val activity = LocalContext.current as Activity
    val viewModel = AppViewModel()
    val isMute = remember{ mutableStateOf(false) }

    val sharedPreferences = activity.getSharedPreferences("learning_app", ComponentActivity.MODE_PRIVATE)
    if(!sharedPreferences.contains("isMute")){
        sharedPreferences.edit()
            .putBoolean("isMute", false)
            .apply()
    }
    isMute.value = sharedPreferences.getBoolean("isMute", false)

    NavHost(navController = navigationController, startDestination = AppScreen.SplashScreen.route) {

        composable(route = AppScreen.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }

        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController = navigationController, isMute = isMute, sharedPreferences = sharedPreferences)
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

        composable(route = AppScreen.ColorListScreen.route) {
            ColorListScreen(navController = navigationController, viewModel, isMute)
        }

        composable(route = AppScreen.ColorDetailScreen.route) {
            ColorDetailScreen(
                viewModel = viewModel,
                navController = navigationController
            )
        }
    }
}