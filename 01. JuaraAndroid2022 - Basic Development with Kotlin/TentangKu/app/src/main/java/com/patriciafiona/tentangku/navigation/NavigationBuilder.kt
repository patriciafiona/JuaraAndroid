package com.patriciafiona.tentangku.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.tentangku.ui.main.home.HomeScreen
import com.patriciafiona.tentangku.ui.signin.SignInScreen

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = TentangkuScreen.SignInScreen.route) {

        composable(route = TentangkuScreen.SignInScreen.route) {
            SignInScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.HomeScreen.route) {
            HomeScreen(navController = navigationController)
        }

    }
}