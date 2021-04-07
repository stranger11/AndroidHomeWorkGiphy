package com.example.giphy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView

class MainActivity : AppCompatActivity() {

   private lateinit var myViewModel: MyViewModel
   private lateinit var recView: RecyclerView
   private lateinit var searchText: EditText
   private lateinit var buttonSearch: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        val repository = Repository()
        val viewModelFactory = MyViewModelFactory(repository)
        myViewModel = ViewModelProvider(this, viewModelFactory).get(MyViewModel::class.java)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recView = findViewById(R.id.recycler_view)
        searchText = findViewById(R.id.search_text)
        buttonSearch = findViewById(R.id.button_search)

        myViewModel.getTrendGifList(
            recView,
            this,
            R.layout.dialog_window,
            R.id.gif_in_window,
            R.id.share_button,
                R.drawable.circle_layout)

        myViewModel.getSearchGifs(
            recView,
            searchText,
            buttonSearch,
            this,
            R.layout.dialog_window,
            R.id.gif_in_window,
            R.id.share_button,
                R.drawable.circle_layout)
    }
}