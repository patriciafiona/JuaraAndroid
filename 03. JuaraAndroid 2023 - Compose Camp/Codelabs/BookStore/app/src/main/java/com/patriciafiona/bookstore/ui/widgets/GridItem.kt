package com.patriciafiona.bookstore.ui.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.patriciafiona.bookstore.R
import com.patriciafiona.bookstore.model.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GridItem(
    book: Book,
    modifier: Modifier = Modifier,
    onDetailsClick: (Book) -> Unit,
) {
    Card(
        onClick = { onDetailsClick(book) },
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(130.dp)
                    .height(150.dp)
                    .padding(bottom = 8.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.volumeInfo.imageLinks?.thumbnail?.replace("http","https"))
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.ic_image_search),
                contentScale = ContentScale.FillBounds
            )

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = book.volumeInfo.title ?: "Unknown title" ,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Start
                ),
                maxLines = 2,
                overflow =  TextOverflow.Ellipsis
            )
        }
    }

}