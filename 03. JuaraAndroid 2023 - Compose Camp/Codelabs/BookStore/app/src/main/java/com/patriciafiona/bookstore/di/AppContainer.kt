package com.patriciafiona.bookstore.di

import com.patriciafiona.bookstore.data.BookshelfRepository
import com.patriciafiona.bookstore.network.BookshelfApiService

interface AppContainer {
    val bookshelfApiService: BookshelfApiService
    val bookshelfRepository: BookshelfRepository
}