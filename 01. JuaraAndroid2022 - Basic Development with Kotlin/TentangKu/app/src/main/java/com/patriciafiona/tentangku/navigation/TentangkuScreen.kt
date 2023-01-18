package com.patriciafiona.tentangku.navigation

sealed class TentangkuScreen(val route: String) {
    object SplashScreen: TentangkuScreen("splash_screen")
    object MainScreen: TentangkuScreen("main_screen")
    object HomeScreen: TentangkuScreen("home_screen")
    object AboutScreen: TentangkuScreen("about_screen")
    object FinanceScreen: TentangkuScreen("finance_screen")
    object NotesScreen: TentangkuScreen("notes_screen")
    object ReminderScreen: TentangkuScreen("reminder_screen")
    object WeightScreen: TentangkuScreen("weight_screen")
    object SignInScreen: TentangkuScreen("signIn_screen")
}