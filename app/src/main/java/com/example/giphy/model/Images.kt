package com.example.giphy.model

import com.google.gson.annotations.SerializedName

data class Images (
    @SerializedName("preview_gif")
    val original : Gif?
)