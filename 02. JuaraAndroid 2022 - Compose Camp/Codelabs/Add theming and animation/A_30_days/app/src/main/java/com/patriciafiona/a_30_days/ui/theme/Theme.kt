/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.patriciafiona.a_30_days.ui.theme

import android.annotation.SuppressLint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.woof.ui.theme.*

private val LightColorPalette = lightColors(
    background = Color.White,
    surface = Platinum,
    onSurface = AshGray,
    primary = AshGray,
    onPrimary = Color.Black,
    secondary = HookersGreen,
    onSecondary = Color.Gray,
)

@SuppressLint("ConflictingOnColor")
private val DarkColorPalette = darkColors(
    background = Color.Black,
    surface = Grey800,
    onSurface = Grey900,
    primary = Grey900,
    onPrimary = White,
    secondary = Color.Black,
    onSecondary = Grey100
)

@Composable
fun A_30_daysTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
