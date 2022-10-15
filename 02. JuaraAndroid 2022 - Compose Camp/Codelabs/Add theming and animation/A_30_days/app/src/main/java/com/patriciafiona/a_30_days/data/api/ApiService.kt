package com.patriciafiona.a_30_days.data.api

import com.patriciafiona.a_30_days.data.model.RecipesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiService {

    @Headers(
        "X-RapidAPI-Host:tasty.p.rapidapi.com"
    )
    @GET("recipes/list")
    fun listRecipes(
        @Header("X-RapidAPI-Key") api_key: String,
        @Query("from") from: Int,
        @Query("size") size: Int,
    ): Call<RecipesResponse>
}