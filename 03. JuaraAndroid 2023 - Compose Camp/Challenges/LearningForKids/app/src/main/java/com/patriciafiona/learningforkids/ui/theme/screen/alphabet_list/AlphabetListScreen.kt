package com.patriciafiona.learningforkids.ui.theme.screen.alphabet_list

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.ui.theme.viewModel.AppViewModel
import com.patriciafiona.learningforkids.ui.theme.brightYellow
import com.patriciafiona.learningforkids.ui.theme.vividOrange
import com.patriciafiona.learningforkids.ui.theme.widget.AlphabetItem
import com.patriciafiona.learningforkids.ui.theme.widget.LottieAnim
import com.patriciafiona.learningforkids.ui.theme.widget.TriangleFlippedShape
import com.patriciafiona.learningforkids.ui.theme.widget.TriangleShape
import com.patriciafiona.learningforkids.utils.Utils
import com.patriciafiona.learningforkids.utils.Utils.dpToPx
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlphabetListScreen(
    navController: NavController,
    viewModel: AppViewModel,
    isMute: MutableState<Boolean>,
){
    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp.dp.dpToPx()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentPos = rememberSaveable{ mutableIntStateOf(0) }
    val bgmSound = remember { MediaPlayer.create(context, R.raw.cute_creatures) }
    val backBtnSound = remember { MediaPlayer.create(context, R.raw.shooting_sound_fx) }
    OnLifecycle(
        bgmSound = bgmSound,
        currentPos = currentPos,
        isMute = isMute
    )

    val listData = viewModel.alphabets

    Utils.setSystemBarColor(color = vividOrange)

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(brightYellow)
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row{
                val shapeSize = 70f
                val totalTriangle = screenWidthPx / shapeSize

                for(i in 1..totalTriangle.toInt() + 1){
                    TriangleFlippedShape(size = shapeSize, color = vividOrange)
                }
            }

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
                            tint = Color.White,
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
                    columns = GridCells.Adaptive(150.dp),

                    // content padding
                    contentPadding = PaddingValues(
                        start = 8.dp,
                        top = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                    content = {
                        items(listData.size) { index ->
                            AlphabetItem(
                                navController,
                                data = listData[index],
                                viewModel
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

            Row{
                val shapeSize = 70f
                val totalTriangle = screenWidthPx / shapeSize

                for(i in 1..totalTriangle.toInt() + 1){
                    TriangleShape(size = shapeSize, color = vividOrange)
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

@Preview(showBackground = true)
@Composable
fun AlphabetListPreview(){
    val navController = rememberNavController()
    val viewModel = AppViewModel()
    val isMute = remember {
        mutableStateOf(false)
    }
    AlphabetListScreen(navController, viewModel, isMute)
}