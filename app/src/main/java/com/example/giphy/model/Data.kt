package com.example.giphy.model

import com.google.gson.annotations.SerializedName

data class Data (
    @SerializedName("data")
    val data: List<GifEntity>?
)