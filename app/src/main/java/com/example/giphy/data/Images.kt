package com.example.giphy.data

import com.google.gson.annotations.SerializedName

data class Images (
    @SerializedName("preview_gif")
    val original : Gif?
)