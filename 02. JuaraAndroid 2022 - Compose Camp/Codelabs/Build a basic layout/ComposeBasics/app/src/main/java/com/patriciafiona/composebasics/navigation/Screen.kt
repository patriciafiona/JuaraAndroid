package com.patriciafiona.composebasics.navigation

sealed class Screen(val route: String){
    object HomeScreen: Screen("home_screen")
    object ComposeArticle: Screen("compose_article_screen")
    object TaskManager: Screen("task_manager_screen")
    object ComposeQuadrant: Screen("compose_quadrant_screen")
}
