package com.patriciafiona.composebasics

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.patriciafiona.composebasics.ui.theme.ComposeBasicsTheme

@Composable
fun ComposeArticle(navController: NavController) {
    Column {
        Banner()
        ArticleTitle("Jetpack Compose Tutorial")
        ArticleParagraph01(stringResource(id = R.string.article_paragraph_01))
        ArticleParagraph02(stringResource(id = R.string.article_paragraph_02))
    }
}

@Composable
fun Banner() {
    val image = painterResource(R.drawable.bg_compose_background)
    Box {
        Image(
            painter = image,
            contentDescription = null,
        )
    }
}

@Composable
fun ArticleTitle(title: String) {
    Text(
        text = title,
        fontSize = 24.sp,
        modifier = Modifier
            .padding(16.dp)

    )
}

@Composable
fun ArticleParagraph01(content: String) {
    Text(
        text = content,
        textAlign = TextAlign.Justify,
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp)

    )
}

@Composable
fun ArticleParagraph02(content: String) {
    Text(
        text = content,
        textAlign = TextAlign.Justify,
        modifier = Modifier
            .padding(16.dp)

    )
}

@Preview(showBackground = true)
@Composable
fun ComposeArticlePreview() {
    ComposeBasicsTheme {
        val navigationController = rememberNavController()
        ComposeArticle(navigationController)
    }
}