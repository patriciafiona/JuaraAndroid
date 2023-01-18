package com.patriciafiona.tentangku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.tentangku.data.source.remote.entity.Main
import com.patriciafiona.tentangku.ui.main.MainScreen
import com.patriciafiona.tentangku.ui.main.home.HomeScreen
import com.patriciafiona.tentangku.ui.signin.SignInScreen
import com.patriciafiona.tentangku.ui.splash.SplashScreen

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = TentangkuScreen.SplashScreen.route) {

        composable(route = TentangkuScreen.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.SignInScreen.route) {
            SignInScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.MainScreen.route) {
            MainScreen(navController = navigationController)
        }

    }
}