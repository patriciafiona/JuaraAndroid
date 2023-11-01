package com.patriciafiona.learningforkids.navigation

sealed class AppScreen(val route: String) {
    object SplashScreen: AppScreen("splash_screen")
    object HomeScreen: AppScreen("home_screen")
    object AlphabetListScreen: AppScreen("alphabet_list_screen")
    object AlphabetDetailScreen: AppScreen("alphabet_detail_screen")
    object ColorListScreen: AppScreen("color_list_screen")
    object ColorDetailScreen: AppScreen("color_detail_screen")
}