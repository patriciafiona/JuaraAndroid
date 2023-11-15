package com.patriciafiona.bookstore.data

import com.patriciafiona.bookstore.model.Book

interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>?

    suspend fun getBook(id: String): Book?
}