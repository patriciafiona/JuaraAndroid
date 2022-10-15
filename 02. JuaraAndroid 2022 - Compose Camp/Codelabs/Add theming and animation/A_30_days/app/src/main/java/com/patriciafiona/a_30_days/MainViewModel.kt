package com.patriciafiona.a_30_days

import android.graphics.Movie
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.patriciafiona.a_30_days.data.api.ApiConfig
import com.patriciafiona.a_30_days.data.model.RecipesItem
import com.patriciafiona.a_30_days.data.model.RecipesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _recipes = MutableLiveData<RecipesResponse>()
    val recipes: LiveData<RecipesResponse> = _recipes

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        val APIKey: String = BuildConfig.RECIPES_API_KEY
    }

    init {
        getRecipes()
    }

    private fun getRecipes() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().listRecipes(APIKey, 0, 30)
        client.enqueue(object : Callback<RecipesResponse> {
            override fun onResponse(
                call: Call<RecipesResponse>,
                response: Response<RecipesResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _recipes.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<RecipesResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}