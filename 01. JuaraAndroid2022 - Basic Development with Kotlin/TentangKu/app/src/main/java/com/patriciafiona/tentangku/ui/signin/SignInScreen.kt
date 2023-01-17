package com.patriciafiona.tentangku.ui.signin

import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.ui.main.ui.theme.*

@Composable
fun SignInScreen (navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Beeswax),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.signin_bg),
            contentScale = ContentScale.Crop,
            contentDescription = "Sign In Image"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Sign in",
            style = TextStyle(
                color = Sepia,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(.6f),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = DimGray)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier
                    .size(20.dp)
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = rememberDrawablePainter(
                            drawable = AppCompatResources.getDrawable(LocalContext.current, R.drawable.google_logo)
                        ),
                        contentScale = ContentScale.Fit,
                        contentDescription = "Google icon"
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = stringResource(id = R.string.sign_in_with_google),
                    color = Color.White
                )
            }
        }
    }
}