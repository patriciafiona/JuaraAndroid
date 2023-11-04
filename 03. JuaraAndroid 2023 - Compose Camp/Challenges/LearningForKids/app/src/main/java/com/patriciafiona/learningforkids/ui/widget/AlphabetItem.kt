package com.patriciafiona.learningforkids.ui.widget

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.patriciafiona.learningforkids.data.entity.Alphabet
import com.patriciafiona.learningforkids.navigation.AppScreen
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.theme.goldVessel
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel
import com.patriciafiona.learningforkids.utils.Utils.getColor
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlphabetItem(data: Alphabet, viewModel: AppViewModel) {
    val character = data.name.first().toString()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val imgLoader = ImageLoader.Builder(context)
        .respectCacheHeaders(false)
        .memoryCachePolicy(CachePolicy.ENABLED)
        .diskCachePolicy(CachePolicy.ENABLED)
        .build()

    val buttonSound = remember { MediaPlayer.create(context, R.raw.button) }

    Coil.setImageLoader(imgLoader)

    Box(modifier = Modifier
        .padding(bottom = 48.dp)
        .clickable {
            coroutineScope.launch {
                launch {
                    buttonSound.start()
                }
                delay(500)

                viewModel.updateCurrentAlphabet(data)
                viewModel.navigateToDetailPage(data)
            }
        }
    ) {
        //Main Container Shadow
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(0, 75)
                }
                .size(140.dp)
                .background(goldVessel)
                .align(Alignment.TopCenter)
        )

        //Main Container
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(0, 35)
                }
                .size(150.dp)
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
                    .size(100.dp)
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
                    fontSize = 30.sp
                )
            )
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
}