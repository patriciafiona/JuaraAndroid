package com.patriciafiona.composebasics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.composebasics.ui.theme.ComposeBasicsTheme


@Composable
fun TaskManager(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image = painterResource(R.drawable.ic_task_completed)
        Box {
            Image(
                painter = image,
                contentDescription = null,
            )
        }

        Text(
            text = "All tasks completed",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 8.dp, top = 24.dp)
        )

        Text(
            text = "Nice work!",
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeBasicsTheme {
        val navigationController = rememberNavController()
        TaskManager(navController = navigationController)
    }
}