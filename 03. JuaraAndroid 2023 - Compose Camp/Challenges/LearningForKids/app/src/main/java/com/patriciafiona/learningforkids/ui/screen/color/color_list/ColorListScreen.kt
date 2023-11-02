package com.patriciafiona.learningforkids.ui.screen.color.color_list

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.brightYellow
import com.patriciafiona.learningforkids.ui.theme.colorPrimary
import com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_list.AlphabetListScreen
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel
import com.patriciafiona.learningforkids.ui.widget.AlphabetItem
import com.patriciafiona.learningforkids.ui.widget.ColorItem
import com.patriciafiona.learningforkids.ui.widget.LottieAnim
import com.patriciafiona.learningforkids.utils.Utils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ColorListScreen(
    navController: NavController,
    viewModel: AppViewModel,
    isMute: MutableState<Boolean>,
    windowSize: WindowWidthSizeClass
){
    val listData = viewModel.colors
    Log.d("Color_List", listData.toList().toString())

    val isInternetConnected = remember{ mutableStateOf(true) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentPos = rememberSaveable{ mutableIntStateOf(0) }
    val bgmSound = remember { MediaPlayer.create(context, R.raw.happy_childhood_loop_173335) }
    val backBtnSound = remember { MediaPlayer.create(context, R.raw.shooting_sound_fx) }
    OnLifecycle(
        bgmSound = bgmSound,
        currentPos = currentPos,
        isMute = isMute
    )

    Utils.setSystemBarColor(color = Color.LightGray)

    Scaffold {
        Box (modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(it)
        ) {
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
                    .fillMaxSize()
                    .padding(it),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(modifier = Modifier.padding(start = 8.dp)){
                    if(listData.size > 0) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    launch {
                                        backBtnSound.start()
                                    }
                                    delay(500)
                                    navController.popBackStack()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Filled.ArrowBack,
                                tint = Color.Black,
                                contentDescription = "Back button",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                }

                if(listData.size > 0) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        columns = GridCells.Adaptive(if(windowSize == WindowWidthSizeClass.Compact) 150.dp else 310.dp),

                        // content padding
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 16.dp
                        ),
                        content = {
                            items(listData.size) { index ->
                                ColorItem(
                                    navController = navController,
                                    data = listData[index],
                                    viewModel = viewModel,
                                    windowSize = windowSize
                                )
                            }
                        }
                    )
                }else if(isInternetConnected.value){
                    Spacer(modifier = Modifier.weight(1f))

                    LottieAnim(
                        anim = R.raw.loading,
                        modifier = Modifier
                            .fillMaxWidth(.7f)
                    )

                    Handler(Looper.getMainLooper()).postDelayed({
                        isInternetConnected.value = Utils.isInternetAvailable()
                    }, 1500)

                    Spacer(modifier = Modifier.weight(1f))
                }else{
                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .clip(CircleShape)
                            .background(Color.Red)
                    ){
                        LottieAnim(
                            anim = R.raw.no_connection,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                    Text(
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        text = "No Internet Connection",
                        style = TextStyle(
                            color = Color.Red,
                            fontSize = 30.sp,
                            fontFamily = PlayoutDemoFont
                        )
                    )

                    Handler(Looper.getMainLooper()).postDelayed({
                        isInternetConnected.value = Utils.isInternetAvailable()
                    }, 100)

                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun OnLifecycle(
    bgmSound: MediaPlayer,
    currentPos: MutableState<Int>,
    isMute: MutableState<Boolean>
) {
    Utils.OnLifecycleEvent { _, event ->
        // do stuff on event
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                bgmSound.isLooping = true

                if (isMute.value) {
                    bgmSound.setVolume(0.0f, 0.0f)
                } else {
                    bgmSound.setVolume(1.0f, 1.0f)
                }

                if (currentPos.value != 0) {
                    bgmSound.seekTo(currentPos.value)
                }
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