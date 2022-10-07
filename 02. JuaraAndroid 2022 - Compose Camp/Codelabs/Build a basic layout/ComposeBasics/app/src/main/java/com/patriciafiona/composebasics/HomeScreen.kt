package com.patriciafiona.composebasics

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.composebasics.navigation.Screen
import com.patriciafiona.composebasics.ui.theme.ComposeBasicsTheme

@Composable
fun HomeScreen(navController: NavController){
    Column {
        Text(
            text = "Welcome to Practice: Compose Basic!",
            fontSize = 36.sp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.main_opening),
            fontSize = 18.sp,
            textAlign = TextAlign.Justify,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.CenterHorizontally)
                .padding(start = 16.dp, top = 16.dp,
                    end = 16.dp, bottom = 24.dp)
        )

        CreateButton(
            "Compose Article",
            icon = Icons.Filled.Favorite,
            onClickAction = {
                navController.navigate(Screen.ComposeArticle.route)
            }
        )

        CreateButton(
            "Task Manager",
            icon = Icons.Filled.List,
            onClickAction = {
                navController.navigate(Screen.TaskManager.route)
            }
        )

        CreateButton(
            "Compose Quadrant",
            icon = Icons.Filled.ArrowForward,
            onClickAction = {
                navController.navigate(Screen.ComposeQuadrant.route)
            }
        )
    }
}

@Composable
fun CreateButton(message: String,
                 icon: ImageVector,
                 onClickAction: (() -> Unit)) {
    Button(
        onClick = onClickAction,
        // Uses ButtonDefaults.ContentPadding by default
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(bottom = 16.dp)
    ) {
        // Inner content including an icon and a text label
        Icon(
            icon,
            contentDescription = "icon button",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            message,
            modifier = Modifier
                .width(200.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ComposeBasicsTheme {
        val navigationController = rememberNavController()
        HomeScreen(navigationController)
    }
}