package com.example.passwordApp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordApp.R
import com.example.passwordApp.utils.Utils

class AddPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_password)
        Utils.hideSystemUI(window)
    }
}