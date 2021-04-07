package com.example.giphy.retrofit

import com.example.giphy.model.Data
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphsApi {

    @GET("search?api_key=56JhaDMz9VasodDoWX5sv1jSOkiKfRWf&limit=30&offset=1&rating=a&lang=en")
    suspend fun getGifList(@Query("q") searchName: String): Data

    @GET("search?api_key=56JhaDMz9VasodDoWX5sv1jSOkiKfRWf&q=trend&limit=30&offset=1&rating=a&lang=en")
    suspend fun getTrendGifList(): Data
}