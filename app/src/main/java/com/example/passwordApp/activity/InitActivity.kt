package com.example.passwordApp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordApp.R
import org.newsapi.NewsApi


class InitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_init)
        NewsApi.initialize("0fd2f1d00d6b49b6a15659a42479513b")
    }
}