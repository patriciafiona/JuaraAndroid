package com.patriciafiona.learningforkids.ui.screen.alphabets.alphabet_list

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.learningforkids.R
import com.patriciafiona.learningforkids.ui.theme.PlayoutDemoFont
import com.patriciafiona.learningforkids.ui.viewModel.AppViewModel
import com.patriciafiona.learningforkids.ui.theme.brightYellow
import com.patriciafiona.learningforkids.ui.theme.vividOrange
import com.patriciafiona.learningforkids.ui.widget.AlphabetItem
import com.patriciafiona.learningforkids.ui.widget.LottieAnim
import com.patriciafiona.learningforkids.ui.widget.TriangleFlippedShape
import com.patriciafiona.learningforkids.ui.widget.TriangleShape
import com.patriciafiona.learningforkids.utils.Utils
import com.patriciafiona.learningforkids.utils.Utils.dpToPx
import com.patriciafiona.learningforkids.utils.Utils.isInternetAvailable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AlphabetListScreen(
    viewModel: AppViewModel,
    modifier: Modifier = Modifier
){
    val configuration = LocalConfiguration.current
    val screenWidthPx = configuration.screenWidthDp.dp.dpToPx()

    val isInternetConnected = remember{ mutableStateOf(true) }

    val listData = viewModel.alphabets
    Log.d("Alphabet_List", listData.toList().toString())

    Utils.setSystemBarColor(color = vividOrange)

    Column(
        modifier = modifier
            .background(brightYellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
        ){
            val shapeSize = 70f
            val totalTriangle = screenWidthPx / shapeSize

            for(i in 1..totalTriangle.toInt() + 1){
                TriangleFlippedShape(size = shapeSize, color = vividOrange)
            }
        }

        if(listData.size > 0) {
            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 8.dp),
                columns = GridCells.Adaptive(150.dp),

                // content padding
                contentPadding = PaddingValues(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 16.dp
                ),
                content = {
                    items(listData.size) { index ->
                        AlphabetItem(
                            data = listData[index],
                            viewModel = viewModel
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
                isInternetConnected.value = isInternetAvailable()
            }, 1500)

            Spacer(modifier = Modifier.weight(1f))
        }else{
            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color.White)
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
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = PlayoutDemoFont
                )
            )

            Handler(Looper.getMainLooper()).postDelayed({
                isInternetConnected.value = isInternetAvailable()
            }, 100)

            Spacer(modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
        ){
            val shapeSize = 70f
            val totalTriangle = screenWidthPx / shapeSize

            for(i in 1..totalTriangle.toInt() + 1){
                TriangleShape(size = shapeSize, color = vividOrange)
            }
        }
    }
}