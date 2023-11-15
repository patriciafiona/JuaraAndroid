package com.patriciafiona.bookstore.network

import com.patriciafiona.bookstore.model.Book
import com.patriciafiona.bookstore.model.QueryResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookshelfApiService {

    companion object {
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }

    /**
     * Returns a [List] of [Book] and this method can be called from a Coroutine.
     * The @GET annotation indicates that the "volumes" endpoint will be requested with the GET
     * HTTP method
     */
    @GET("volumes")
    suspend fun getBooks(
        @Query("q") query: String,
        @Query("key") key: String,
    ): Response<QueryResponse>

    @GET("volumes/{id}")
    suspend fun getBook(
        @Path("id") id: String,
        @Query("key") key: String
    ): Response<Book>
}