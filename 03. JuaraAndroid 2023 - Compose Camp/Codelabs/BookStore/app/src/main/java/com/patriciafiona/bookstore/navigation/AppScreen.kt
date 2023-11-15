package com.patriciafiona.bookstore.navigation

sealed class AppScreen (val route: String) {
    object SplashScreen: AppScreen("splash_screen")
    object HomeScreen: AppScreen("home_screen")
    object DetailScreen: AppScreen("detail_screen")
}