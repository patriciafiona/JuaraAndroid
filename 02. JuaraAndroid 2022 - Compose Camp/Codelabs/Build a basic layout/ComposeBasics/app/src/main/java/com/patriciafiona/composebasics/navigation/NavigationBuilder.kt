package com.patriciafiona.composebasics.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.composebasics.ComposeArticle
import com.patriciafiona.composebasics.ComposeQuadrant
import com.patriciafiona.composebasics.HomeScreen
import com.patriciafiona.composebasics.TaskManager

@Composable
fun NavigationBuilder() {
    val navigationController = rememberNavController()
    NavHost(navController = navigationController, startDestination = Screen.HomeScreen.route){
        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navigationController)
        }
        composable(route = Screen.ComposeArticle.route){
            ComposeArticle(navController = navigationController)
        }
        composable(route = Screen.TaskManager.route){
            TaskManager(navController = navigationController)
        }
        composable(route = Screen.ComposeQuadrant.route){
            ComposeQuadrant(navController = navigationController)
        }
    }
}