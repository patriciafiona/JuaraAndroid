package com.patriciafiona.bookstore.data

import com.patriciafiona.bookstore.model.Book
import com.patriciafiona.bookstore.network.BookshelfApiService

class DefaultBookshelfRepository(
    private val bookshelfApiService: BookshelfApiService
) : BookshelfRepository {
    /** Retrieves list of Volumes from underlying data source */
    override suspend fun getBooks(query: String): List<Book>? {
        return try {
            val res = bookshelfApiService.getBooks(query)
            if (res.isSuccessful) {
                res.body()?.items ?: emptyList()
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun getBook(id: String): Book? {
        return try {
            val res = bookshelfApiService.getBook(id)
            if (res.isSuccessful) {
                res.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}