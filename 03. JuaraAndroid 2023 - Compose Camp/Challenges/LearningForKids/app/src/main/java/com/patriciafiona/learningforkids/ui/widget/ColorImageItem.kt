package com.patriciafiona.learningforkids.ui.widget

import android.media.MediaPlayer
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.data.entity.ColorData
import com.patriciafiona.learningforkids.navigation.AppScreen
import com.patriciafiona.learningforkids.ui.theme.NilamTracingFont
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.blanchedAlmond
import com.patriciafiona.learningforkids.ui.theme.goldVessel
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel
import com.patriciafiona.learningforkids.utils.Utils.getColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun ColorImageItem(
    url: String,
    name: String,
    color: String,
    windowSize: WindowWidthSizeClass
) {
    val context = LocalContext.current

    var tts: TextToSpeech? = null
    var isBtnEnabled by remember { mutableStateOf(true) }
    val pitchRate by remember { mutableFloatStateOf(1f) }
    val speechRate by remember { mutableFloatStateOf(1f) }

    Card(
        modifier = Modifier
            .width(if(windowSize == WindowWidthSizeClass.Compact) 150.dp else 400.dp)
            .height(if(windowSize == WindowWidthSizeClass.Compact) 200.dp else 400.dp)
            .padding(if(windowSize == WindowWidthSizeClass.Compact) 8.dp else 16.dp)
            .clickable {
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
                                name,
                                TextToSpeech.QUEUE_ADD,
                                null,
                                null
                            )
                        }
                    }
                }
                isBtnEnabled = true
            },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))

                if(!isBtnEnabled){
                    Icon(
                        Icons.Filled.VolumeUp,
                        tint = Color.DarkGray,
                        contentDescription = "Character Sound",
                        modifier = Modifier
                            .size(if(windowSize == WindowWidthSizeClass.Compact) 30.dp else 60.dp)
                    )
                }else {
                    Icon(
                        Icons.Filled.VolumeUp,
                        tint = getColor(color),
                        contentDescription = "Character Sound",
                        modifier = Modifier
                            .size(if(windowSize == WindowWidthSizeClass.Compact) 30.dp else 60.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.image_placeholder),
                    contentDescription = "Character image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(if(windowSize == WindowWidthSizeClass.Compact) 100.dp else 200.dp)
                        .padding(top = 16.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    text = name,
                    style = TextStyle(
                        color = Color.Black,
                        fontFamily = NilamTracingFont,
                        fontSize = if(windowSize == WindowWidthSizeClass.Compact) 30.sp else 60.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }
    }
}