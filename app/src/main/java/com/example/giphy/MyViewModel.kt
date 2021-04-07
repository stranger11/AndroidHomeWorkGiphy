package com.example.giphy

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.example.giphy.adapter.GifAdapter
import com.example.giphy.model.Data
import com.example.giphy.model.Gif
import kotlinx.coroutines.launch

@Suppress("UNCHECKED_CAST")
class MyViewModel(private val repository: Repository) : ViewModel() {

    private var _getGiff: MutableLiveData<Data> = MutableLiveData()
    private var getGiff: LiveData<Data> = _getGiff

    private var _getSearchGiff: MutableLiveData<Data> = MutableLiveData()
    private var getSearchGiff: LiveData<Data> = _getSearchGiff

    fun getTrendGifList(
        recyclerView: RecyclerView,
        context: Context,
        layout: Int,
        viewGif: Int,
        button: Int,
        radiusDialog: Int
    ) {
        viewModelScope.launch {
            val response = repository.getGifs()
            _getGiff.value = response
            with(recyclerView) {
                layoutManager = StaggeredGridLayoutManager(
                    2,
                    StaggeredGridLayoutManager.VERTICAL
                )
                getGiff.observeForever { response ->
                    adapter = GifAdapter(response
                        .data
                        ?.map { it.images?.original }
                            as List<Gif>) {
                        gifDialogWindow(
                                it,
                                context,
                                layout,
                                viewGif,
                                button,
                                radiusDialog)
                    }
                    hasFixedSize()
                }
            }
        }
    }

    fun getSearchGifs(
        recyclerView: RecyclerView,
        searchName: EditText,
        buttonSearch: LottieAnimationView,
        context: Context,
        layout: Int,
        viewGif: Int,
        button: Int,
        radiusDialog: Int
    ) {
        buttonSearch.playAnimation()
        buttonSearch.setOnClickListener {
            buttonSearch.playAnimation()
            val searchGif = searchName.text.toString()
            viewModelScope.launch {
                val response = repository.getSearchGifs(searchName = searchGif)
                _getSearchGiff.value = response
                with(recyclerView) {
                    layoutManager = StaggeredGridLayoutManager(
                        2,
                        StaggeredGridLayoutManager.VERTICAL
                    )
                    getSearchGiff.observeForever { response ->
                        adapter = GifAdapter(response
                            .data
                            ?.map { it.images?.original }
                                as List<Gif>) {
                            gifDialogWindow(
                                    it,
                                    context,
                                    layout,
                                    viewGif,
                                    button,
                                    radiusDialog)
                        }
                        hasFixedSize()
                    }
                }
            }
        }
    }

    private fun gifDialogWindow(
        gif: Gif,
        context: Context,
        layout: Int,
        viewGif: Int,
        button: Int,
        radiusDialog: Int
    ) {
        val dialog = Dialog(context)
        dialog.window?.setBackgroundDrawableResource(radiusDialog)
        dialog.setContentView(layout)
        val gifInWindow = dialog.findViewById<ImageView>(viewGif)
        val shareButton = dialog.findViewById<Button>(button)
        Glide.with(context).load(gif.gifUrl).thumbnail(0.1f)
            .into(gifInWindow)

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, gif.gifUrl)
            type = "text/plain"
        }

        shareButton.setOnClickListener {
            context.startActivity(Intent.createChooser(sendIntent, null))
            dialog.dismiss()
        }
        dialog.show()
    }
}







