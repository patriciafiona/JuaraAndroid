package com.patriciafiona.learningforkids.navigation

sealed class AppScreen(val route: String) {
    object SplashScreen: AppScreen("splash_screen")
    object HomeScreen: AppScreen("home_screen")
    object AlphabetScreen: AppScreen("alphabet_screen")
    object ColorListScreen: AppScreen("color_list_screen")
    object ColorDetailScreen: AppScreen("color_detail_screen")
}