package com.patriciafiona.learningforkids.ui.theme.screen.home

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.patriciafiona.learningforkids.utils.Utils.getTimeGreetingStatus

@Composable
fun HomeScreen(navController: NavController){
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
                        onCLickAction = { },
                        btnSize = 50,
                        btnColor = brightRed,
                        btnOutlineColor = vividRed,
                        btnIcon = Icons.Filled.Settings,
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

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                        .clickable { navController.navigate(AppScreen.AlphabetListScreen.route) },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Yellow,
                    ),
                ) {
                    Box (modifier = Modifier.fillMaxSize()){
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

                        Row(
                            verticalAlignment = Alignment.Bottom
                        ) {
                            Text(
                                "A",
                                style = TextStyle(
                                    fontFamily = PlayoutDemoFont,
                                    fontSize = 80.sp,
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
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}