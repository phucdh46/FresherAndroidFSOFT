package com.example.day4dialogviewpapersettingscren

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.day4dialogviewpapersettingscren.adapter.ViewPagerAdapter
import com.example.day4dialogviewpapersettingscren.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //viewpager
        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.viewPager.adapter = viewPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Dialog"
                1 -> tab.text = "Setting"
                else -> tab.text = ""
            }
        }.attach()
    }
}