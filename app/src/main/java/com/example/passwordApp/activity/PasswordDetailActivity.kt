package com.example.passwordApp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.passwordApp.R
import com.example.passwordApp.fragment.PasswordDetailFragment
import com.example.passwordApp.utils.Utils

class PasswordDetailActivity : AppCompatActivity() {

    private var site: String? = null
    private var password: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_detail)
        Utils.hideSystemUI(window)
        site = intent.getStringExtra("Site")
        password = intent.getStringExtra("Password")
        if (site != null && password != null) {
            val fragment = PasswordDetailFragment.newInstance(site!!, password!!)
            val fragmentManager = supportFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.passwordDetailsFragmentContainerView, fragment)
                .commit()
        }
    }
}