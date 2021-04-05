package com.example.giphy

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        hideSystemUI()
        addGif()
        transitionIntoMainActivity()
    }

    private fun addGif() {
        val icon = findViewById<ImageView>(R.id.splash_screen_image)
        Glide.with(this)
            .load("https://static.wikia.nocookie.net/henrystickmin/images/8/8c/Henry_Stickmin_Distract_%27Flash_Version%27.gif")
            .thumbnail(0.1f)
            .into(icon)
    }

    @Suppress("DEPRECATION")
    private fun transitionIntoMainActivity() {
        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }

    @Suppress("DEPRECATION")
    private fun hideSystemUI() {
        if (android.os.Build.VERSION.SDK_INT <= android.os.Build.VERSION_CODES.R) {
            window.decorView.apply {
                systemUiVisibility = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_FULLSCREEN)
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideSystemUI()
    }
}
