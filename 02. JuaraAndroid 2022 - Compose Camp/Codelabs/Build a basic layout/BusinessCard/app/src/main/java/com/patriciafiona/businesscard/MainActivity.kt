package com.patriciafiona.businesscard

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patriciafiona.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MyCard()
                }
            }
        }
    }
}

@Composable
fun MyCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(13, 57, 72, 255))
    ) {
        Spacer(modifier = Modifier.weight(1.0f))

        LogoNameTitle(
            name = "Patricia Fiona",
            title = "Android & iOS Enthusiasm"
        )

        Spacer(modifier = Modifier.weight(1.0f))

        ContactInformation(
            "+11 (123) 444 555 666",
            "https://g.dev/patriciafiona",
            "patriciafiona3@gmail.com"
        )
    }
}

@Composable
fun LogoNameTitle(name: String, title: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(top = 16.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
    ) {
        val image = painterResource(R.drawable.android_logo)
        Box {
            Canvas(modifier = Modifier.size(100.dp), onDraw = {
                drawCircle(color = Color.White)
            })
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .width(100.dp)
                    .padding(all = 16.dp)
            )
        }

        Text(
            text = name,
            color = Color.White,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 3.dp, top = 16.dp)
        )

        Text(
            text = title,
            color = Color.Green,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}

@Composable
fun ContactInformation(
    phone: String,
    developerProfile: String,
    email: String
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .padding(vertical = 16.dp)
    ){
        Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp))

        ContactItem(phone, Icons.Filled.Phone, false)

        Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp))

        ContactItem(developerProfile, Icons.Filled.Person, true)

        Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp))

        ContactItem(email, Icons.Filled.Email, false)

        Divider(startIndent = 8.dp, thickness = 1.dp, color = Color.Gray,
            modifier = Modifier.padding(bottom = 8.dp))
    }
}
@Composable
fun ContactItem(message: String, icon: ImageVector, isClickable: Boolean){
    val context = LocalContext.current
    val intent = remember { Intent(Intent.ACTION_VIEW, Uri.parse(message)) }

    Row(
        modifier = Modifier.padding(bottom = 8.dp)
    ){
        Icon(
            icon,
            contentDescription = "icon",
            tint = Color.Green,
            modifier = Modifier
                .height(50.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            contentPadding = PaddingValues(),
            modifier = Modifier
                .defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
            onClick = {
            if (isClickable){
                context.startActivity(intent)
            }
        }) {
            Text(
                text = message,
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier
                    .width(230.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BusinessCardTheme {
        MyCard()
    }
}