package com.patriciafiona.tentangku.ui.main.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.patriciafiona.tentangku.R
import com.patriciafiona.tentangku.ui.main.ui.theme.AppleBlossom
import com.patriciafiona.tentangku.ui.main.ui.theme.NightShadz

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AboutScreen (navController: NavController) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = NightShadz
    )

    val backdropScaffoldState = rememberBackdropScaffoldState(
        BackdropValue.Concealed
    )

    BackdropScaffold(
        scaffoldState = backdropScaffoldState,
        modifier = Modifier
            .background(AppleBlossom)
            .padding(top = 26.dp)
            .navigationBarsPadding()
            .fillMaxSize(),
        peekHeight = (LocalConfiguration.current.screenHeightDp * 0.2).dp,
        headerHeight = 60.dp,
        gesturesEnabled = false,
        appBar = {
            TopAppBar(
                backgroundColor = AppleBlossom,
                elevation = 0.dp
            ) {
                IconButton(
                    onClick = {
                        navController.navigateUp()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back icon",
                        tint = Color.White
                    )
                }
            }
        },
        backLayerContent = {
           Box(
               modifier = Modifier
                   .fillMaxSize()
                   .background(AppleBlossom)
           )
        },
        frontLayerContent = { 
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                 verticalArrangement = Arrangement.Center,
                 horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    stringResource(id = R.string.about),
                    style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                SubSection(
                    title = stringResource(id = R.string.juaraAndroid_final_submission),
                    imageResource = R.drawable.juara_android_2022_poster,
                    desc = stringResource(id = R.string.about_details),
                    date = "April 10, 2022"
                )

                Spacer(modifier = Modifier.height(8.dp))

                SubSection(
                    title = stringResource(id = R.string.challenge_accepted),
                    imageResource = R.drawable.compose_migration_2023,
                    desc = stringResource(id = R.string.compose_migration_desc),
                    date = "January 24, 2023"
                )

                Spacer(modifier = Modifier.height(50.dp))

                Text(
                    text = stringResource(id = R.string.copyright_txt),
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.LightGray
                )
            }
        }
    )
}

@Composable
fun SubSection (
    title: String,
    imageResource: Int?,
    desc: String,
    date: String
){
    Column(
        modifier = Modifier
            .padding(top = 30.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Brightness1,
                contentDescription = "Sub-section Arrow",
                tint = Color.LightGray,
                modifier = Modifier
                    .size(20.dp)
                    .padding(end = 8.dp)
            )
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Start,
                    color = AppleBlossom
                )
                Text(
                    text = "Posted on $date",
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Start,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        imageResource?.let { painterResource(id = it) }?.let {
            Image(
                painter = it,
                contentDescription = "Poster"
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = desc,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(device = Devices.PIXEL_2_XL, showBackground = true)
@Preview(device = Devices.PIXEL_4, showBackground = true)
@Composable
fun AboutScreenPreview() {
    val navController = rememberNavController()
    AboutScreen(navController)
}