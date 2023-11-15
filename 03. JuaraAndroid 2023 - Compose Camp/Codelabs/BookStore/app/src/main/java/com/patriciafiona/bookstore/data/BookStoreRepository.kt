package com.patriciafiona.bookstore.data

import com.patriciafiona.bookstore.model.Book

interface BookStoreRepository {
    suspend fun getBooks(): List<Book>
}

/**
 * Network Implementation of repository that retrieves amphibian data from underlying data source.
 */
interface BookshelfRepository {
    suspend fun getBooks(query: String): List<Book>?

    suspend fun getBook(id: String): Book?
}