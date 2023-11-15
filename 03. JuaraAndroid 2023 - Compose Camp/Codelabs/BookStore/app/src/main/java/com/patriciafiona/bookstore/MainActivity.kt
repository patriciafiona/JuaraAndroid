package com.patriciafiona.bookstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.patriciafiona.bookstore.navigation.NavigationBuilder
import com.patriciafiona.bookstore.ui.theme.BookStoreTheme

class MainActivity : ComponentActivity() {
    companion object{
        var API_KEY = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set API KEY
        API_KEY = resources.getString(R.string.book_api_key)

        setContent {
            BookStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationBuilder()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BookStorePreview() {
    BookStoreTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationBuilder()
        }
    }
}