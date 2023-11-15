package com.patriciafiona.bookstore.ui.screens.detail

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.patriciafiona.bookstore.R
import com.patriciafiona.bookstore.model.Book
import com.patriciafiona.bookstore.ui.viewModel.BookViewModel
import com.patriciafiona.bookstore.ui.widgets.GoogleColorLine
import com.webtoonscorp.android.readmore.foundation.ReadMoreTextOverflow
import com.webtoonscorp.android.readmore.foundation.ToggleArea
import com.webtoonscorp.android.readmore.material3.ReadMoreText

@Composable
fun DetailScreen(
    navController: NavController,
    viewModel: BookViewModel
){
    val book = viewModel.selectedBook
    val (expanded, onExpandedChange) = rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleColorLine()

        Row{
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back button"
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){
                AsyncImage(
                    modifier = Modifier
                        .width(130.dp)
                        .height(160.dp)
                        .align(Alignment.Center),
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http","https"))
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_broken_image),
                    placeholder = painterResource(id = R.drawable.ic_image_search),
                    contentScale = ContentScale.Crop
                )
            }

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = book.volumeInfo.title ?: "Unknown title" ,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                ),
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = book.volumeInfo.subtitle ?: "No subtitle" ,
                style = TextStyle(
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic
                ),
            )

            Spacer(modifier = Modifier.height(16.dp))

            ReadMoreText(
                text = book.volumeInfo.description ?: "No description",
                expanded = expanded,
                onExpandedChange = onExpandedChange,
                modifier = Modifier.fillMaxWidth(),
                readMoreText = "Read more",
                readMoreColor = Color.Black,
                readMoreFontSize = 15.sp,
                readMoreFontStyle = FontStyle.Normal,
                readMoreFontWeight = FontWeight.Bold,
                readMoreFontFamily = FontFamily.Default,
                readMoreTextDecoration = TextDecoration.None,
                readMoreMaxLines = 3,
                readMoreOverflow = ReadMoreTextOverflow.Ellipsis,
                readLessText = "Read less",
                readLessColor = Color.Black,
                readLessFontSize = 15.sp,
                readLessFontStyle = FontStyle.Normal,
                readLessFontWeight = FontWeight.Bold,
                readLessFontFamily = FontFamily.Default,
                readLessTextDecoration = TextDecoration.None,
            )
        }
    }
}