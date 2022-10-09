package com.patriciafiona.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patriciafiona.lemonade.ui.theme.LemonadeTheme

/*
Developer: Patricia Fiona
Created: 08 October 2022

Notes: This project made by my own style and logic,
       you can follow the tips template in the codelab page.

       This code is more efficient because I use same template
       but adding additional logic in Page 'Squeeze Lemon'
 */

class SelectionActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LemonApp()
                }
            }
        }
    }
}

@Composable
fun LemonApp() {
    var pos by remember { mutableStateOf(0) }

    var squeeze by remember { mutableStateOf(0) }
    var mySqueeze by remember { mutableStateOf(0) }

    val detail = stringArrayResource(R.array.lemon_content_detail)
    val desc = stringArrayResource(R.array.lemon_content_description)
    val imageResource = when(pos) {
        0 -> R.drawable.lemon_tree
        1 -> R.drawable.lemon_squeeze
        2 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = detail[pos],
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        Button(
            onClick = {
                  if (pos == 1){
                      squeeze = (2..4).random()
                      if(mySqueeze < squeeze){
                          mySqueeze++
                      }else{
                          pos++
                      }
                  }else if (pos == 3){
                      // Back to the beginning
                      pos = 0
                  }else{
                      //Go to Next Page
                      pos++
                  }
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Transparent,
                disabledBackgroundColor = Color.Transparent,
            ),
            border = BorderStroke(2.dp, Color(105, 205, 216, 255)),
            modifier = Modifier
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(imageResource),
                contentDescription = null
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LemonadeTheme {
        LemonApp()
    }
}