package com.patriciafiona.tentangku.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.tentangku.data.source.local.entity.FinanceTransaction
import com.patriciafiona.tentangku.data.source.local.entity.Note
import com.patriciafiona.tentangku.data.source.local.entity.Reminder
import com.patriciafiona.tentangku.data.source.local.entity.Weight
import com.patriciafiona.tentangku.ui.main.about.AboutScreen
import com.patriciafiona.tentangku.ui.main.finance.FinanceScreen
import com.patriciafiona.tentangku.ui.main.finance.addUpdate.FinanceAddUpdateScreen
import com.patriciafiona.tentangku.ui.main.home.HomeScreen
import com.patriciafiona.tentangku.ui.main.notes.NotesScreen
import com.patriciafiona.tentangku.ui.main.notes.addUpdate.NotesAddUpdateScreen
import com.patriciafiona.tentangku.ui.main.reminder.ReminderScreen
import com.patriciafiona.tentangku.ui.main.reminder.addUpdate.ReminderAddUpdateScreen
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
            HomeScreen(navController = navigationController, appCompatActivity = appCompatActivity)
        }

        composable(route = TentangkuScreen.AboutScreen.route) {
            AboutScreen(navController = navigationController)
        }

        composable(route = TentangkuScreen.FinanceScreen.route) {
            FinanceScreen(navController = navigationController, appCompatActivity = appCompatActivity)
        }

        composable(route = TentangkuScreen.FinanceAddUpdateScreen.route) { previousBackStackEntry ->
            val transaction = previousBackStackEntry.arguments?.getParcelable<FinanceTransaction>("transaction")
            if (transaction != null) {
                FinanceAddUpdateScreen(navController = navigationController, finance = transaction, appCompatActivity = appCompatActivity)
            }else{
                FinanceAddUpdateScreen(navController = navigationController, finance = null, appCompatActivity = appCompatActivity)
            }
        }

        composable(route = TentangkuScreen.NotesScreen.route) {
            NotesScreen(navController = navigationController, appCompatActivity = appCompatActivity)
        }

        composable(route = TentangkuScreen.NotesAddUpdateScreen.route) { previousBackStackEntry ->
            val notes = previousBackStackEntry.arguments?.getParcelable<Note>("notes")
            if (notes != null) {
                NotesAddUpdateScreen(navController = navigationController, notes = notes, appCompatActivity = appCompatActivity)
            }else{
                NotesAddUpdateScreen(navController = navigationController, notes = null, appCompatActivity = appCompatActivity)
            }
        }

        composable(route = TentangkuScreen.ReminderScreen.route) {
            ReminderScreen(navController = navigationController, appCompatActivity = appCompatActivity)
        }

        composable(route = TentangkuScreen.ReminderAddUpdateScreen.route) { previousBackStackEntry ->
            val reminder = previousBackStackEntry.arguments?.getParcelable<Reminder>("reminder")
            if (reminder != null) {
                ReminderAddUpdateScreen(navController = navigationController, reminder = reminder, appCompatActivity = appCompatActivity)
            }else{
                ReminderAddUpdateScreen(navController = navigationController, reminder = null, appCompatActivity = appCompatActivity)
            }
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