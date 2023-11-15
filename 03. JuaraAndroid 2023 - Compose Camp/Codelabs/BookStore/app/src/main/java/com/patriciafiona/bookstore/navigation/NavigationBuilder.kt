package com.patriciafiona.bookstore.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.bookstore.ui.screens.HomeScreen
import com.patriciafiona.bookstore.ui.screens.SplashScreen

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = AppScreen.SplashScreen.route
    ) {
        composable(route = AppScreen.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }

        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController = navigationController)
        }

//        composable(route = MarioScreen.DetailCharacterScreen.route) { previousBackStackEntry ->
//            val data: com.patriciafiona.marioworld.data.entities.Character? = previousBackStackEntry.arguments?.getParcelable("character")
//            if (data != null) {
//                EnterAnimationFadeIn (durationInMillis = 1550) {
//                    CharacterDetail(
//                        navController = navigationController,
//                        character = data,
//                        isMute = isMute,
//                        windowSize
//                    )
//                }
//            }
//        }
    }
}