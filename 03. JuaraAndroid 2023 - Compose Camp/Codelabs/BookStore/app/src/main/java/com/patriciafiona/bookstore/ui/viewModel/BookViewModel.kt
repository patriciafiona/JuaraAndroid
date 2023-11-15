package com.patriciafiona.bookstore.ui.viewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.patriciafiona.bookstore.data.BookshelfRepository
import com.patriciafiona.bookstore.model.Book
import com.patriciafiona.bookstore.ui.BookStoreApplication
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface QueryUiState {
    data class Success(val bookshelfList: List<Book>) : QueryUiState
    object Error : QueryUiState
    object Loading : QueryUiState
}

private val TAG = "BookViewModel"

class BookViewModel(
    private val bookshelfRepository: BookshelfRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<QueryUiState>(QueryUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getAllBooks()
    }

    lateinit var selectedBook: Book

    fun getBooks(query: String = "") {
        viewModelScope.launch {
            _uiState.value = QueryUiState.Loading

            _uiState.value = try {
                val books = bookshelfRepository.getBooks(query)
                if (books == null) {
                    QueryUiState.Error
                } else if (books.isEmpty()){
                    QueryUiState.Success(emptyList())
                } else{
                    QueryUiState.Success(books)
                }
            } catch (e: IOException) {
                QueryUiState.Error
            } catch (e: HttpException) {
                QueryUiState.Error
            }
        }
    }

    fun getAllBooks() {
        Log.d(TAG, "Started to getAllBooks()....")

        val topics = arrayListOf(
            "Biology", "Flower", "Science", "Soccer", "Dog", "Plant", "Cooking", "Recipe",
            "Human", "Cat", "Cow", "Sugar", "Junk Food", "Android", "Kotlin", "Java", "iOS"
        )

        viewModelScope.launch {
            _uiState.value = QueryUiState.Loading

            _uiState.value = try {
                val books = bookshelfRepository.getBooks(topics.random())
                if (books == null) {
                    Log.d(TAG, "Failed to getAllBooks()")
                    QueryUiState.Error
                } else if (books.isEmpty()){
                    Log.d(TAG, "Empty when getAllBooks()...")
                    QueryUiState.Success(emptyList())
                } else{
                    Log.d(TAG, "Success to getAllBooks(), data:$books")
                    QueryUiState.Success(books)
                }
            } catch (e: IOException) {
                QueryUiState.Error
            } catch (e: HttpException) {
                QueryUiState.Error
            }
        }
    }

    /**
     * Factory for [BookViewModel] that takes [BookshelfRepository] as a dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                        as BookStoreApplication)
                val bookshelfRepository = application.container.bookshelfRepository
                BookViewModel(bookshelfRepository = bookshelfRepository)
            }
        }
    }
}