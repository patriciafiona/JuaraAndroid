package com.patriciafiona.learningforkids.navigation

sealed class AppScreen(val route: String) {
    object SplashScreen: AppScreen("splash_screen")
    object HomeScreen: AppScreen("home_screen")
    object AlphabetListScreen: AppScreen("alphabet_list_screen")
}