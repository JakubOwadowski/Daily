package com.example.passwordApp.utils

import android.R
import android.content.Context
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import kotlin.math.sqrt


object Utils {
    fun hideSystemUI(window: Window) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        WindowInsetsControllerCompat(window,
            window.decorView.findViewById(R.id.content)).let { controller ->
            controller.hide(WindowInsetsCompat.Type.systemBars())
            controller.systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
    }

    fun largeScreen(context: Context): Boolean {

        val metrics = context.resources.displayMetrics;
        val width = metrics.widthPixels
        val height = metrics.heightPixels
        val diagonalInches = sqrt((width * width + height * height).toDouble())

        return diagonalInches >= 3000
    }
}