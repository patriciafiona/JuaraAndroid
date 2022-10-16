package com.example.lunchtray.navigation

import androidx.annotation.StringRes
import com.example.lunchtray.R

// TODO: Screen enum
enum class Screen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    EntreeMenu(title = R.string.entree_menu),
    SideDishMenu(title = R.string.side_dish_menu),
    AccompanimentMenu(title = R.string.accompaniment_menu),
    Summary(title = R.string.order_summary)
}