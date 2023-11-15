package com.patriciafiona.bookstore.ui.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.patriciafiona.bookstore.R

@Composable
fun Loader(modifier: Modifier = Modifier, animFile: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animFile))
    Box(modifier = Modifier.fillMaxWidth()){
        LottieAnimation(
            composition = composition,
            modifier = modifier
                .align(Alignment.Center)
                .fillMaxSize(),
            iterations = LottieConstants.IterateForever,
        )
    }
}