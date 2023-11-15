package com.patriciafiona.bookstore.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.bookstore.ui.screens.home.HomeScreen
import com.patriciafiona.bookstore.ui.screens.splash.SplashScreen
import com.patriciafiona.bookstore.ui.viewModel.BookViewModel

@Composable
fun NavigationBuilder() {
    val viewModel : BookViewModel = viewModel(factory = BookViewModel.Factory)
    val navigationController = rememberNavController()

    NavHost(
        navController = navigationController,
        startDestination = AppScreen.SplashScreen.route
    ) {
        composable(route = AppScreen.SplashScreen.route) {
            SplashScreen(navController = navigationController)
        }

        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(
                viewModel = viewModel,
                retryAction = viewModel::getAllBooks,
                navController = navigationController
            )
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