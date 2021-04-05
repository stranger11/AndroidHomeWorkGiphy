package com.example.giphy.data

import android.provider.MediaStore
import com.google.gson.annotations.SerializedName

data class GifEntity (
    @SerializedName("images")
    val images: Images?
)