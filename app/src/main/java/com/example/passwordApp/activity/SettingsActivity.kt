package com.example.passwordApp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordApp.R
import com.example.passwordApp.utils.Utils

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        Utils.hideSystemUI(window)
    }
}