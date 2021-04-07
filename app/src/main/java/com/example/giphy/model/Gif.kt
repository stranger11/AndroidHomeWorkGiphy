package com.example.giphy.model

import com.google.gson.annotations.SerializedName

data class Gif (
    @SerializedName("url")
    val gifUrl: String
)