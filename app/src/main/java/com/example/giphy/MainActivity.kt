package com.example.giphy

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.giphy.data.Gif
import com.example.giphy.databinding.ActivityMainBinding
import com.example.giphy.retrofit.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getMemGifList()
        addToRecyclerViewAsGridLayoutManager()


    }

    private fun showSharePopupDialog(gif: Gif) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.share_popup_window)
        val gifInPopup = dialog.findViewById<ImageView>(R.id.gif_in_popup)
        val shareButton = dialog.findViewById<AppCompatButton>(R.id.share_button)
        val linkGif = gif.gifUrl
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, linkGif)
            type = "text/plain"
        }
        val shareIntent = Intent.createChooser(sendIntent, null)

        Glide.with(this).load(gif.gifUrl).thumbnail(0.1f)
            .into(gifInPopup)

        shareButton.setOnClickListener {
            startActivity(shareIntent)
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getMemGifList() {
        recyclerView = findViewById(R.id.recycler_view)

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitClient.instance.getMemGifList()
            withContext(Dispatchers.Main) {
                with(recyclerView) {
                    layoutManager = GridLayoutManager(
                        this@MainActivity,
                        3,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                    adapter = GifAdapter(response.data?.map { it.images?.original } as List<Gif>) {
                        showSharePopupDialog(it)
                    }
                    hasFixedSize()
                }
            }
        }
    }

    private fun addToRecyclerViewAsGridLayoutManager() {
        recyclerView = findViewById(R.id.recycler_view)
        val searchName = findViewById<EditText>(R.id.search_text)
        val searchButton = findViewById<ImageButton>(R.id.button_search)
        val builder = AlertDialog.Builder(this)

        searchButton.setOnClickListener {
            val searchGif = searchName.text.toString()
            if (searchGif.isEmpty()) {
                with(builder) {
                    setTitle("dddd")
                    //setIcon(R.drawable.attention)
                    setPositiveButton("Ok") { dialog, _ -> dialog.cancel() }
                    setCancelable(false)
                    create()
                    show()
                }
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = RetrofitClient.instance.getGifList(searchName = searchGif)
                    withContext(Dispatchers.Main) {
                        with(recyclerView) {
                            layoutManager = GridLayoutManager(
                                this@MainActivity,
                                3,
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                            adapter =
                                GifAdapter(response.data?.map { it.images?.original }
                                        as List<Gif>) {
                                    showSharePopupDialog(it)
                                }
                            hasFixedSize()
                        }
                    }
                }
            }
        }
    }


}