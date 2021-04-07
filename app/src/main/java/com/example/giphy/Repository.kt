package com.example.giphy

import com.example.giphy.model.Data
import com.example.giphy.retrofit.RetrofitClient

class Repository {

    suspend fun getGifs() : Data {
        return RetrofitClient.INSTANCE.getTrendGifList()
    }

    suspend fun getSearchGifs(searchName: String) : Data {
        return RetrofitClient.INSTANCE.getGifList(searchName)
    }
}