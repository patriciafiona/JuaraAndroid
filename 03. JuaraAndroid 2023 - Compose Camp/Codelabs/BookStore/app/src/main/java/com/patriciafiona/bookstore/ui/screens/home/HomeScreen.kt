package com.patriciafiona.bookstore.ui.screens.home

import android.R.attr.maxLines
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.patriciafiona.bookstore.R
import com.patriciafiona.bookstore.model.Book
import com.patriciafiona.bookstore.ui.theme.GoogleBlue
import com.patriciafiona.bookstore.ui.theme.GoogleGreen
import com.patriciafiona.bookstore.ui.theme.GoogleRed
import com.patriciafiona.bookstore.ui.theme.GoogleYellow
import com.patriciafiona.bookstore.ui.viewModel.BookViewModel
import com.patriciafiona.bookstore.ui.viewModel.QueryUiState
import com.patriciafiona.bookstore.ui.widgets.GridItem
import com.patriciafiona.bookstore.ui.widgets.Loader


private val TAG = "HomeScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: BookViewModel,
    retryAction: () -> Unit,
    navController: NavController
){
    val uiState = viewModel.uiState.collectAsState().value
    var queryString by remember {
        mutableStateOf("")
    }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.dp)
            ){
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(GoogleBlue)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(GoogleRed)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(GoogleYellow)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(GoogleBlue)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(GoogleGreen)
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(GoogleRed)
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Image(
                    modifier = Modifier
                        .fillMaxWidth(.4f)
                        .padding(16.dp),
                    painter = painterResource(id = R.drawable.google_book_logo),
                    contentScale = ContentScale.Fit,
                    contentDescription = "Google Book Logo"
                )

                SearchBar(
                    modifier = Modifier.weight(1f),
                    query = queryString,
                    onQueryChange = { newQueryString ->
                        queryString = newQueryString
                        viewModel.getBooks(queryString)
                    },
                    onSearch = {},
                    placeholder = {
                        Text(text = "Search book here...")
                    },
                    leadingIcon = {},
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            tint = MaterialTheme.colorScheme.onSurface,
                            contentDescription = null
                        )
                    },
                    content = {},
                    active = false,
                    onActiveChange = {},
                    tonalElevation = 0.dp,
                )
            }

            when (uiState) {
                is QueryUiState.Loading -> {
                    Spacer(modifier = Modifier.weight(1f))

                    Loader(animFile = R.raw.book_animation)

                    Spacer(modifier = Modifier.weight(1f))
                }
                is QueryUiState.Success ->
                    BooksListScreen(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        bookshelfList = uiState.bookshelfList,
                    )
                else -> ErrorScreen(modifier = Modifier.weight(1f), retryAction = retryAction)
            }
        }
    }
}

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Log.e(TAG, "ERROR")

    Column(
        modifier = modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(
            modifier = Modifier.padding(16.dp),
            onClick = retryAction
        ) {
            Text(stringResource(R.string.retry))
        }
    }
}

@Composable
private fun BooksListScreen(
    bookshelfList: List<Book>,
    modifier: Modifier = Modifier,
) {
    Log.e(TAG, "LIST")

    if (bookshelfList.isEmpty()) {
        Column(
            modifier = modifier,
        ) {
            Spacer(modifier = Modifier.weight(1f))

            Loader(animFile = R.raw.no_data_animation)

            Spacer(modifier = Modifier.weight(1f))
        }
    } else {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(12.dp),
        ) {
            items(
                items = bookshelfList,
            ) {
                GridItem(
                    book = it,
                    onDetailsClick = {

                    },
                )
            }
        }
    }
}
