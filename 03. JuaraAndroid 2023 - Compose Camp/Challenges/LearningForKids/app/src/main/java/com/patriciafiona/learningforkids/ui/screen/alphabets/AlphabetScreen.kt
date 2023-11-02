package com.patriciafiona.learningforkids.ui.screen.alphabets

import android.app.Activity
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.data.entity.Alphabet
import com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_detail.AlphabetDetailScreen
import com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_list.AlphabetListScreen
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.brightYellow
import com.patriciafiona.learningforkids.ui.theme.vividOrange
import com.patriciafiona.learningforkids.ui.viewModel.AnimalAlphabetUiState
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel
import com.patriciafiona.learningforkids.ui.widget.AlphabetItem
import com.patriciafiona.learningforkids.ui.widget.LottieAnim
import com.patriciafiona.learningforkids.utils.ContentType
import com.patriciafiona.learningforkids.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlphabetScreen(
    navController: NavController,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
    viewModel: AppViewModel,
    isMute: MutableState<Boolean>,
) {
    val context = LocalContext.current
    val currentPos = rememberSaveable{ mutableIntStateOf(0) }
    val bgmSound = remember { MediaPlayer.create(context, R.raw.cute_creatures) }
    val coroutineScope = rememberCoroutineScope()
    val backBtnSound = remember { MediaPlayer.create(context, R.raw.shooting_sound_fx) }

    OnLifecycle(
        bgmSound = bgmSound,
        currentPos = currentPos,
        isMute = isMute
    )

    //adaptive layout
    val uiState by viewModel.uiState.collectAsState()

    val contentType: ContentType = when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Medium -> {
            ContentType.LIST_ONLY
        }
        WindowWidthSizeClass.Expanded -> {
            ContentType.LIST_AND_DETAIL
        }
        else -> {
            ContentType.LIST_ONLY
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (contentType == ContentType.LIST_AND_DETAIL) {
                AlphabetListAndDetailContent(
                    modifier = Modifier.weight(1f),
                    coroutineScope = coroutineScope,
                    backBtnSound = backBtnSound,
                    navController = navController
                )
            } else {
                if (uiState.isShowingListPage) {
                    BackButton(coroutineScope, backBtnSound, navController)

                    AlphabetListScreen(
                        viewModel = viewModel,
                        modifier = modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                } else {
                    val alphabet = uiState.currentAlphabet
                    Log.e("selected", alphabet.toString())
                    AlphabetDetailScreen(
                        viewModel,
                        modifier = modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}

@Composable
private fun BackButton(
    coroutineScope: CoroutineScope,
    backBtnSound: MediaPlayer,
    navController: NavController,
    isLargeScreen: Boolean = false
) {
    Row(
        modifier = Modifier
            .background(vividOrange)
            .padding(start = 8.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
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
                    .size(if (isLargeScreen) 40.dp else 30.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            "Back",
            style = TextStyle(
                color = Color.White,
                fontSize = if (isLargeScreen) 24.sp else 18.sp,
                fontFamily = PlayoutDemoFont
            )
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun AlphabetListAndDetailContent(
    modifier: Modifier = Modifier,
    coroutineScope: CoroutineScope,
    backBtnSound: MediaPlayer,
    navController: NavController
){
    val viewModel: AppViewModel = viewModel()

    Column(modifier = Modifier.fillMaxSize()) {
        BackButton(coroutineScope, backBtnSound, navController, true)

        Row(modifier = modifier) {
            AlphabetListScreen(
                viewModel = viewModel,
                modifier = Modifier
                    .weight(.3f)
                    .fillMaxHeight()
            )

            if(viewModel.isDetailReady.value) {
                if (viewModel.isDetailLoading.value){
                    Column(
                        modifier = Modifier
                            .weight(.7f)
                            .fillMaxHeight()
                            .background(vividOrange),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Spacer(modifier = Modifier.weight(1f))

                        LottieAnim(
                            anim = R.raw.loading,
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }else {
                    AlphabetDetailScreen(
                        viewModel,
                        modifier = Modifier
                            .weight(.7f)
                            .fillMaxHeight(),
                        isListAndDetailView = true
                    )
                }
            }else{
                if (viewModel.isDetailLoading.value){
                    Column(
                        modifier = Modifier
                            .weight(.7f)
                            .fillMaxHeight()
                            .background(vividOrange),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Spacer(modifier = Modifier.weight(1f))

                        LottieAnim(
                            anim = R.raw.loading,
                            modifier = Modifier
                                .fillMaxWidth(.7f)
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }else {
                    Box(
                        modifier = Modifier
                            .weight(.7f)
                            .fillMaxHeight()
                            .background(vividOrange)
                    )
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