package com.example.giphy.data

import com.google.gson.annotations.SerializedName

data class Gif (
    @SerializedName("url")
    val gifUrl: String
)