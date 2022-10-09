package com.example.day4dialogviewpapersettingscren.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.day4dialogviewpapersettingscren.fragment.DialogFragment
import com.example.day4dialogviewpapersettingscren.fragment.SettingFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DialogFragment()
            1 -> return SettingFragment()
            else -> return DialogFragment()
        }
    }

}