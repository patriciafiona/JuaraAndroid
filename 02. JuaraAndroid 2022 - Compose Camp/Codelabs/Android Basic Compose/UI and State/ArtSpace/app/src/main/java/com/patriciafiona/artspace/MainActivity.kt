package com.patriciafiona.artspace

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patriciafiona.artspace.Utils.DateFormater
import com.patriciafiona.artspace.data.Data
import com.patriciafiona.artspace.data.Gallery
import com.patriciafiona.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ArtSpace()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArtSpace() {
    var position by remember { mutableStateOf(0) }
    val data = Data.getAllData()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        UserProfile(
            data = data[position], 
            modifier = Modifier.weight(2f)
        )

        ArtworkWall(
            image = data[position].image,
            modifier = Modifier
                .weight(7f)
        )

        ArtDetails(
            data = data[position],
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(.9f)
                .weight(3f)
        )

        Spacer(modifier = Modifier.weight(1f))
        Row(
            Modifier.weight(2f, true),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = {
                      if (position == 0){
                          position = data.size - 1
                      }else{
                          position--
                      }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Previous")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (position == data.size - 1){
                        position = 0
                    }else{
                        position++
                    }
                },
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Next")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserProfile(data: Gallery, modifier: Modifier){
    Row(
        modifier = modifier
    ) {
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(
                text = "Tanaka Tatsuya",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Text(
                text = DateFormater(data.added),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun ArtworkWall(image: Int, modifier: Modifier){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
    ){
        Image(
            painter = painterResource(image),
            contentDescription = null,
            modifier = modifier
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArtDetails(data: Gallery, modifier: Modifier){
    val padding = 20.dp
    val density = LocalDensity.current

    Card(
        shape = RectangleShape,
        elevation = 12.dp,
        modifier = Modifier
            .padding(padding)
            .drawWithContent {
                val paddingPx = with(density) { padding.toPx() }
                clipRect(
                    left = -paddingPx,
                    top = 0f,
                    right = size.width + paddingPx,
                    bottom = size.height + paddingPx
                ) {
                    this@drawWithContent.drawContent()
                }
            }
    ) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = data.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(bottom = 8.dp)
            )
            Text(
                text = data.description,
                textAlign = TextAlign.Justify,
                fontSize = 12.sp
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ArtSpaceTheme {
        ArtSpace()
    }
}