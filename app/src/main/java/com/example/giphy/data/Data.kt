package com.example.giphy.data

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("data")
    val data: List<GifEntity>?
)