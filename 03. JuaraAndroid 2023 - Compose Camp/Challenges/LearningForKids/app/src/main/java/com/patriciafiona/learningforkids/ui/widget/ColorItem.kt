package com.patriciafiona.learningforkids.ui.widget

import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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

@Composable
fun ColorItem(
    data: ColorData,
    navController: NavController,
    viewModel: AppViewModel,
    windowSize: WindowWidthSizeClass
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val buttonSound = remember { MediaPlayer.create(context, R.raw.button) }

    Box(modifier = Modifier
        .padding(bottom = 24.dp)
        .clickable {
            coroutineScope.launch {
                launch {
                    buttonSound.start()
                }
                delay(500)
                viewModel.selectedColorData.value = data
                navController.navigate(AppScreen.ColorDetailScreen.route)
            }
        },
    ) {
        //Main Container Shadow
        Box(
            modifier = Modifier
                .offset {
                    IntOffset(0, 65)
                }
                .size(if(windowSize == WindowWidthSizeClass.Compact) 140.dp else 280.dp)
                .background(goldVessel)
                .align(Alignment.TopCenter)
        )

        //Main Container
        Column(
            modifier = Modifier
                .offset {
                    IntOffset(0, 35)
                }
                .size(if(windowSize == WindowWidthSizeClass.Compact) 150.dp else 300.dp)
                .background(blanchedAlmond)
                .align(Alignment.TopCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Box(
                modifier = Modifier
                    .size(if(windowSize == WindowWidthSizeClass.Compact) 70.dp else 140.dp)
                    .clip(CircleShape)
                    .background(getColor(data.color))
                    .padding(bottom = 16.dp)
            )

            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                text = data.name,
                style = TextStyle(
                    color = Color.Black,
                    fontFamily = PlayoutDemoFont,
                    fontSize = if(windowSize == WindowWidthSizeClass.Compact) 30.sp else 60.sp,
                    textAlign = TextAlign.Center
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
                    color = Color.DarkGray,
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