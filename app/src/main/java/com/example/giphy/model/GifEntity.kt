package com.example.giphy.model

import com.google.gson.annotations.SerializedName

data class GifEntity (
    @SerializedName("images")
    val images: Images?
)