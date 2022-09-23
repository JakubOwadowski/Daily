package com.example.passwordApp.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.passwordApp.fragment.NewsReelFragment
import com.example.passwordApp.fragment.PasswordsFragment

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    var fragment: Fragment? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0) {
            fragment = NewsReelFragment()
        }
        if (position == 1) {
            fragment = PasswordsFragment()
        }
        return fragment!!
    }
}