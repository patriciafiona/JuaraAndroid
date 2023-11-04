package com.patriciafiona.learningforkids.navigation

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.ui.screen.alphabets.AlphabetScreen
import com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_detail.AlphabetDetailScreen
import com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_list.AlphabetListScreen
import com.patriciafiona.learningforkids.ui.screen.color.color_detail.ColorDetailScreen
import com.patriciafiona.learningforkids.ui.screen.color.color_list.ColorListScreen
import com.patriciafiona.learningforkids.ui.screen.home.HomeScreen
import com.patriciafiona.learningforkids.ui.screen.splash.SplashScreen
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel

@Composable
fun NavigationBuilder(windowSize: WindowWidthSizeClass) {
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
            HomeScreen(
                navController = navigationController,
                isMute = isMute,
                sharedPreferences = sharedPreferences,
                windowSize = windowSize
            )
        }

        composable(route = AppScreen.AlphabetScreen.route) {
            AlphabetScreen(
                navController = navigationController,
                windowSize = windowSize,
                viewModel = viewModel,
                isMute = isMute
            )
        }

        composable(route = AppScreen.ColorListScreen.route) {
            ColorListScreen(
                navController = navigationController,
                viewModel,
                isMute,
                windowSize
            )
        }

        composable(route = AppScreen.ColorDetailScreen.route) {
            ColorDetailScreen(
                viewModel = viewModel,
                navController = navigationController,
                windowSize = windowSize
            )
        }
    }
}