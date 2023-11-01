package com.patriciafiona.learningforkids.ui.theme.screen.home

import android.app.Activity
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.MusicOff
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.navigation.AppScreen
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.brightRed
import com.patriciafiona.learningforkids.ui.theme.colorPrimary
import com.patriciafiona.learningforkids.ui.theme.vividOrange
import com.patriciafiona.learningforkids.ui.theme.vividRed
import com.patriciafiona.learningforkids.ui.theme.widget.CircleButton
import com.patriciafiona.learningforkids.ui.theme.widget.LottieAnim
import com.patriciafiona.learningforkids.utils.Utils
import com.patriciafiona.learningforkids.utils.Utils.OnLifecycleEvent
import com.patriciafiona.learningforkids.utils.Utils.getTimeGreetingStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
    isMute: MutableState<Boolean>,
    sharedPreferences: SharedPreferences
){
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val currentPos = rememberSaveable{ mutableIntStateOf(0) }
    val buttonSound = remember { MediaPlayer.create(context, R.raw.decidemp) }
    val bgmSound = remember { MediaPlayer.create(context, R.raw.funny_bgm) }

    OnLifecycle(
        buttonSound = buttonSound,
        bgmSound = bgmSound,
        currentPos = currentPos,
        isMute = isMute
    )

    Utils.setSystemBarColor(color = colorPrimary)
    
    Scaffold {
        Box (modifier = Modifier
            .fillMaxSize()
            .background(colorPrimary)
            .padding(it)
        ){
            Image(
                painter = painterResource(id = R.drawable.pattern_toys),
                contentDescription = "background pattern",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(.3f)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = "Splash image",
                        modifier = Modifier
                            .width(50.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    CircleButton(
                        onCLickAction = {
                            val currentState = sharedPreferences.getBoolean("isMute", false)
                            sharedPreferences.edit()
                                .putBoolean("isMute", !currentState)
                                .apply()
                            isMute.value = !currentState

                            bgmSound.start()
                            if(isMute.value) {
                                bgmSound.setVolume(0.0f, 0.0f)
                            }else{
                                bgmSound.setVolume(1.0f, 1.0f)
                            }
                        },
                        btnSize = 50,
                        btnColor = brightRed,
                        btnOutlineColor = vividRed,
                        btnIcon = if (isMute.value) { Icons.Filled.MusicOff } else{ Icons.Filled.MusicNote },
                        btnIconColor = Color.White
                    )
                }

                Text(
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
                    text = getTimeGreetingStatus(),
                    style = TextStyle(
                        color = Color.White,
                        fontFamily = PlayoutDemoFont,
                        fontSize = 32.sp
                    )
                )

                AnimalAlphabetCard(coroutineScope, buttonSound, navController)
                ColorCard(coroutineScope, buttonSound, navController)
            }
        }
    }
}

@Composable
private fun ColorCard(
    coroutineScope: CoroutineScope,
    buttonSound: MediaPlayer,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clickable {
                coroutineScope.launch {
                    launch {
                        buttonSound.start()
                    }
                    delay(500)
                    navController.navigate(AppScreen.ColorListScreen.route)
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.pattern_star),
                contentDescription = "pattern background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            LottieAnim(
                modifier = Modifier
                    .fillMaxSize(.6f)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
                R.raw.color_animation
            )

            Column {
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        "C",
                        style = TextStyle(
                            fontFamily = PlayoutDemoFont,
                            fontSize = 70.sp,
                            color = Color.DarkGray
                        )
                    )

                    Text(
                        "olor",
                        style = TextStyle(
                            fontFamily = PlayoutDemoFont,
                            fontSize = 34.sp,
                            color = Color.DarkGray
                        ),
                        modifier = Modifier
                            .padding(bottom = 22.dp),
                    )
                }
            }

            LottieAnim(
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                R.raw.play_animation
            )
        }
    }
}

@Composable
private fun AnimalAlphabetCard(
    coroutineScope: CoroutineScope,
    buttonSound: MediaPlayer,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp)
            .clickable {
                coroutineScope.launch {
                    launch {
                        buttonSound.start()
                    }
                    delay(500)
                    navController.navigate(AppScreen.AlphabetListScreen.route)
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.Yellow,
        ),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.pattern_star),
                contentDescription = "pattern background",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            LottieAnim(
                modifier = Modifier
                    .fillMaxSize(.6f)
                    .fillMaxHeight()
                    .align(Alignment.CenterEnd),
                R.raw.croc_animation
            )

            Column {
                Row(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        "A",
                        style = TextStyle(
                            fontFamily = PlayoutDemoFont,
                            fontSize = 60.sp,
                            color = Color.Magenta
                        )
                    )

                    Text(
                        "nimal",
                        style = TextStyle(
                            fontFamily = PlayoutDemoFont,
                            fontSize = 24.sp,
                            color = Color.Magenta
                        ),
                        modifier = Modifier
                            .padding(bottom = 22.dp),
                    )
                }

                Text(
                    "Alphabet",
                    style = TextStyle(
                        fontFamily = PlayoutDemoFont,
                        fontSize = 24.sp,
                        color = Color.Magenta
                    ),
                    modifier = Modifier
                        .padding(bottom = 22.dp, start = 34.dp)
                        .offset {
                            IntOffset(0, -50)
                        }
                )
            }

            LottieAnim(
                modifier = Modifier
                    .width(70.dp)
                    .height(70.dp)
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                R.raw.play_animation
            )
        }
    }
}

@Composable
private fun OnLifecycle(
    buttonSound: MediaPlayer,
    bgmSound: MediaPlayer,
    currentPos: MutableState<Int>,
    isMute: MutableState<Boolean>
) {
    OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                buttonSound.isLooping = false
                bgmSound.isLooping = true

                if(isMute.value) {
                    bgmSound.setVolume(0.0f, 0.0f)
                }else{
                    bgmSound.setVolume(1.0f, 1.0f)
                }

                if (currentPos.value != 0) {
                    bgmSound.seekTo(currentPos.value)
                }
                buttonSound.seekTo(0)
                bgmSound.start()
            }
            Lifecycle.Event.ON_PAUSE -> {
                currentPos.value = bgmSound.currentPosition
                bgmSound.pause()
            }
            Lifecycle.Event.ON_DESTROY -> {
                currentPos.value = 0

                bgmSound.stop()
                bgmSound.release()
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val activity = LocalContext.current as Activity
    val navController = rememberNavController()
    val isMute = remember {
        mutableStateOf(false)
    }
    val sharedPreferences = activity.getSharedPreferences("learning_app", ComponentActivity.MODE_PRIVATE)
    HomeScreen(
        navController = navController,
        isMute = isMute,
        sharedPreferences
    )
}