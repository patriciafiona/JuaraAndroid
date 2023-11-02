package com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_detail

import android.media.MediaPlayer
import android.net.Uri
import android.speech.tts.TextToSpeech
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.Coil
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.ui.theme.NilamTracingFont
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.goldVessel
import com.patriciafiona.learningforkids.ui.theme.vividOrange
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel
import com.patriciafiona.learningforkids.ui.widget.TitleWithIcon
import com.patriciafiona.learningforkids.ui.widget.TriangleFlippedShape
import com.patriciafiona.learningforkids.ui.widget.YoutubeScreen
import com.patriciafiona.learningforkids.utils.ContentType
import com.patriciafiona.learningforkids.utils.Utils
import com.patriciafiona.learningforkids.utils.Utils.dpToPx
import com.patriciafiona.learningforkids.utils.Utils.getColor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.Locale

@Composable
fun AlphabetDetailScreen(
    viewModel:AppViewModel,
    modifier: Modifier = Modifier,
    isListAndDetailView: Boolean = false
){
    val uiState by viewModel.uiState.collectAsState()
    val data = uiState.currentAlphabet

    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp.dp.dpToPx()

    val gradientColors = listOf(getColor(data.color), getColor(data.color_dark))
    val character = if(data.name.isNotEmpty()) data.name.first().toString() else ""
    val context = LocalContext.current
    val imgLoader = ImageLoader.Builder(context)
        .respectCacheHeaders(false)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    var tts: TextToSpeech? = null
    var isBtnEnabled by remember { mutableStateOf(true) }
    val pitchRate by remember { mutableFloatStateOf(1f) }
    val speechRate by remember { mutableFloatStateOf(1f) }
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }

    val coroutineScope = rememberCoroutineScope()
    val backBtnSound = remember { MediaPlayer.create(context, R.raw.shooting_sound_fx) }

    Coil.setImageLoader(imgLoader)

    if(!isListAndDetailView) {
        Utils.setStatusBarColor(color = getColor(data.color))
        Utils.setNavigationBarColor(color = getColor(data.color_dark))
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { selectedFileUri = it }
        }

    LaunchedEffect(selectedFileUri) {
        selectedFileUri?.let { uri ->
            try {
                val content = withContext(Dispatchers.IO) {
                    val inputStream = context.contentResolver.openInputStream(uri)
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val stringBuilder = StringBuilder()
                    var line: String? = reader.readLine()
                    while (line != null) {
                        stringBuilder.append(line)
                        line = reader.readLine()
                    }
                    reader.close()
                    stringBuilder.toString()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    Box(
        modifier = modifier
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        getColor(data.color),
                        getColor(data.color_dark)
                    )
                )
            ),
    ){
        Image(
            painter = painterResource(id = R.drawable.pattern_alphabets),
            contentDescription = "background pattern",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(.3f)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(!isListAndDetailView) {
                Row(modifier = Modifier.padding(start = 8.dp, top = 16.dp)) {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                launch {
                                    backBtnSound.start()
                                }
                                delay(500)
                                viewModel.navigateToListPage()
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
                    Spacer(modifier = Modifier.weight(1f))
                }
            }else{
                Spacer(modifier = Modifier.height(32.dp))
            }

            Box(modifier = Modifier
                .padding(bottom = 48.dp, top = 16.dp)
            ) {
                //Main Container Shadow
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(0, 75)
                        }
                        .size(300.dp)
                        .background(goldVessel)
                        .align(Alignment.TopCenter)
                )

                //Main Container
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(0, 35)
                        }
                        .size(310.dp)
                        .background(Color.White)
                        .align(Alignment.TopCenter)
                ){
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.img_url)
                            .crossfade(true)
                            .build(),
                        placeholder = painterResource(R.drawable.image_placeholder),
                        contentDescription = "Character image",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(150.dp)
                            .align(Alignment.Center),
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp),
                        text = "${character.uppercase()}${character.lowercase()}",
                        style = TextStyle(
                            color = getColor(data.color_dark),
                            fontFamily = PlayoutDemoFont,
                            fontSize = 80.sp
                        )
                    )

                    Text(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(horizontal = 16.dp, vertical = 32.dp),
                        text = data.phonic,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 30.sp
                        )
                    )

                    IconButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        onClick = {
                            if (isBtnEnabled)
                                isBtnEnabled = false
                            tts = TextToSpeech(
                                context
                            ) {
                                if (it == TextToSpeech.SUCCESS) {
                                    tts?.let { txtToSpeech ->
                                        txtToSpeech.language = Locale.ENGLISH
                                        txtToSpeech.setPitch(pitchRate)
                                        txtToSpeech.setSpeechRate(speechRate)
                                        txtToSpeech.speak(
                                            character,
                                            TextToSpeech.QUEUE_ADD,
                                            null,
                                            null
                                        )
                                    }
                                }
                            }
                            isBtnEnabled = true
                        }
                    ) {
                        if(!isBtnEnabled){
                            Icon(
                                Icons.Filled.VolumeUp,
                                tint = Color.DarkGray,
                                contentDescription = "Character Sound",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }else {
                            Icon(
                                Icons.Filled.VolumeUp,
                                tint = getColor(data.color_dark),
                                contentDescription = "Character Sound",
                                modifier = Modifier
                                    .size(40.dp)
                            )
                        }
                    }
                }

                //Pin Circle
                Box(
                    contentAlignment= Alignment.Center,
                    modifier = Modifier
                        .size(32.dp)
                        .border(
                            width = 7.dp,
                            color = getColor(data.color_dark),
                            shape = CircleShape
                        )
                        .align(Alignment.TopCenter),
                ){
                    Box(
                        modifier = Modifier
                            .size(25.dp)
                            .clip(CircleShape)
                            .background(getColor(data.color)),
                    )
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .clip(RoundedCornerShape(50))
                    .background(Color.White),
                text = data.name,
                style = TextStyle(
                    brush = Brush.linearGradient(
                        colors = gradientColors
                    ),
                    fontFamily = PlayoutDemoFont,
                    fontSize = 52.sp,
                    textAlign = TextAlign.Center
                )
            )

            TitleWithIcon(
                text = "Writing",
                textSize = 38,
                textColor = Color.White,
                iconImage = R.drawable.icon_star
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth(.7f)
                    .height(240.dp)
                    .padding(bottom = 32.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
            ){
                Box {
                    Text(
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center)
                            .padding(vertical = 30.dp),
                        text = "${character.uppercase()}${character.lowercase()}",
                        style = TextStyle(
                            color = Color.Black,
                            fontFamily = NilamTracingFont,
                            fontSize = 150.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            TitleWithIcon(
                text = "Video",
                textSize = 38,
                textColor = Color.White,
                iconImage = R.drawable.icon_star
            )

            YoutubeScreen(
                videoId = data.videoId,
                modifier = Modifier
                    .fillMaxWidth(.8f)
            )
            Text(
                "Video Copyright © 2016 Smart Study Co., Ltd.\nAll Rights Reserved.",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp, bottom = 32.dp, top = 16.dp)
            )
        }

        if(isListAndDetailView) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RectangleShape)
                    .align(Alignment.TopCenter)
            ) {
                val shapeSize = 70f
                val totalTriangle = screenWidthPx / shapeSize

                for (i in 1..totalTriangle.toInt() + 1) {
                    TriangleFlippedShape(size = shapeSize, color = vividOrange)
                }
            }
        }
    }
}