package com.patriciafiona.tentangku.navigation

sealed class TentangkuScreen(val route: String) {
    object SplashScreen: TentangkuScreen("splash_screen")
    object HomeScreen: TentangkuScreen("home_screen")
    object AboutScreen: TentangkuScreen("about_screen")
    object FinanceScreen: TentangkuScreen("finance_screen")
    object FinanceAddUpdateScreen: TentangkuScreen("finance__add_update_screen")
    object NotesScreen: TentangkuScreen("notes_screen")
    object NotesAddUpdateScreen: TentangkuScreen("notes_add_update_screen")
    object ReminderScreen: TentangkuScreen("reminder_screen")
    object ReminderAddUpdateScreen: TentangkuScreen("reminder_add_update_screen")
    object WeightScreen: TentangkuScreen("weight_screen")
    object WeightAddUpdateScreen: TentangkuScreen("weight_add_update_screen")
    object WeatherScreen: TentangkuScreen("weather_screen")
    object SignInScreen: TentangkuScreen("signIn_screen")
}