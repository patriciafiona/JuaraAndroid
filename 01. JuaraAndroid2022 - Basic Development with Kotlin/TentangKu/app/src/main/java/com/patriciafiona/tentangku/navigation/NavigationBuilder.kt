package com.patriciafiona.tentangku.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.ui.main.about.AboutScreen
import com.patriciafiona.tentangku.ui.main.finance.FinanceScreen
import com.patriciafiona.tentangku.ui.main.home.HomeScreen
import com.patriciafiona.tentangku.ui.main.notes.NotesScreen
import com.patriciafiona.tentangku.ui.main.reminder.ReminderScreen
import com.patriciafiona.tentangku.ui.main.weather.WeatherScreen
import com.patriciafiona.tentangku.ui.main.weight.WeightScreen
import com.patriciafiona.tentangku.ui.main.weight.addUpdate.WeightAddUpdateScreen
import com.patriciafiona.tentangku.ui.signin.SignInScreen
import com.patriciafiona.tentangku.ui.splash.SplashScreen

@Composable
fun NavigationBuilder(appCompatActivity: AppCompatActivity) {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = TentangkuScreen.SplashScreen.route) {

        composable(route = TentangkuScreen.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.SignInScreen.route) {
            SignInScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.HomeScreen.route) {
            HomeScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.AboutScreen.route) {
            AboutScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.FinanceScreen.route) {
            FinanceScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.NotesScreen.route) {
            NotesScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.ReminderScreen.route) {
            ReminderScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.WeatherScreen.route) {
            WeatherScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.WeightScreen.route) {
            WeightScreen(navController = navigationController, appCompatActivity = appCompatActivity)
        }

        composable(route = TentangkuScreen.WeightAddUpdateScreen.route) { previousBackStackEntry ->
            val weight = previousBackStackEntry.arguments?.getParcelable<Weight>("weight")
            if (weight != null) {
                WeightAddUpdateScreen(navController = navigationController, weight = weight, appCompatActivity = appCompatActivity)
            }else{
                WeightAddUpdateScreen(navController = navigationController, weight = null, appCompatActivity = appCompatActivity)
            }
        }

    }
}