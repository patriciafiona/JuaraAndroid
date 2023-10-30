package com.patriciafiona.learningforkids.ui.theme.screen.splash

import android.media.MediaPlayer
import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.navigation.AppScreen
import com.patriciafiona.learningforkids.ui.theme.colorPrimary
import com.patriciafiona.learningforkids.utils.Utils.setSystemBarColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavController){
    setSystemBarColor(colorPrimary)
    val context = LocalContext.current
    val bgmSound = remember { MediaPlayer.create(context, R.raw.click) }

    val scale = remember {
        Animatable(0.0f)
    }

    LaunchedEffect(key1 = true) {
        bgmSound.start()
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(800, easing = {
                OvershootInterpolator(4f).getInterpolation(it)
            })
        )
        delay(1000)
        navController.navigate(AppScreen.HomeScreen.route) {
            popUpTo(AppScreen.SplashScreen.route) {
                inclusive = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.app_icon),
            contentDescription = "Splash image",
            modifier = Modifier
                .scale(scale.value)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview(){
    val navController = rememberNavController()
    SplashScreen(navController = navController)
}