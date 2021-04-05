package com.example.giphy.retrofit

import com.example.giphy.retrofit.service.APIGif
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.giphy.com/v1/gifs/"
    val instance: APIGif = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(APIGif::class.java)
}