package com.patriciafiona.bookstore.di

import com.patriciafiona.bookstore.data.BookshelfRepository
import com.patriciafiona.bookstore.data.DefaultBookshelfRepository
import com.patriciafiona.bookstore.network.BookshelfApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class DefaultAppContainer : AppContainer {
    override val bookshelfApiService: BookshelfApiService by lazy {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BookshelfApiService.BASE_URL)
            .build()
            .create()
    }

    override val bookshelfRepository: BookshelfRepository by lazy {
        DefaultBookshelfRepository(bookshelfApiService)
    }
}