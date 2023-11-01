package com.patriciafiona.learningforkids.ui.theme.screen.color_detail

import android.media.MediaPlayer
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.viewModel.AppViewModel
import com.patriciafiona.learningforkids.ui.theme.widget.ColorImageItem
import com.patriciafiona.learningforkids.ui.theme.widget.ColorItem
import com.patriciafiona.learningforkids.ui.theme.widget.LottieAnim
import com.patriciafiona.learningforkids.utils.Utils
import com.patriciafiona.learningforkids.utils.Utils.getColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ColorDetailScreen(
    navController: NavController,
    viewModel: AppViewModel,
){
    val selectedColor = viewModel.selectedColorData.value
    Utils.setSystemBarColor(color = getColor(selectedColor.color))

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentPos = rememberSaveable{ mutableIntStateOf(0) }
    val bgmSound = remember { MediaPlayer.create(context, R.raw.cute_creatures) }
    val backBtnSound = remember { MediaPlayer.create(context, R.raw.shooting_sound_fx) }

    Scaffold {
        Box (modifier = Modifier
            .fillMaxSize()
            .background(getColor(selectedColor.color))
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
                    if(selectedColor.images.size > 0) {
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth(.6f)
                        .padding(bottom = 32.dp, top = 16.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = "Colors",
                        style = TextStyle(
                            color = getColor(selectedColor.color),
                            fontFamily = PlayoutDemoFont,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }

                if(selectedColor.images.size > 0) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 8.dp),
                        columns = GridCells.Adaptive(150.dp),

                        // content padding
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            top = 16.dp,
                            end = 16.dp,
                            bottom = 8.dp
                        ),
                        content = {
                            items(selectedColor.images.size) { index ->
                                ColorImageItem(
                                    navController = navController,
                                    url = selectedColor.images.values.elementAt(index),
                                    name = selectedColor.images.keys.elementAt(index),
                                    color = selectedColor.color,
                                    viewModel = viewModel
                                )
                            }
                        }
                    )
                }else{
                    Spacer(modifier = Modifier.weight(1f))

                    LottieAnim(
                        anim = R.raw.loading,
                        modifier = Modifier
                            .fillMaxWidth(.7f)
                    )

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