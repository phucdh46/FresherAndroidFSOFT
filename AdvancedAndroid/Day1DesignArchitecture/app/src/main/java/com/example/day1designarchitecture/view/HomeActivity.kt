package com.example.day1designarchitecture.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.day1designarchitecture.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    val SHARED_PREFS = "shared_prefs"
    private lateinit var sharedpreferences: SharedPreferences
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)
        val editor = sharedpreferences.edit()
        binding.btnLogout.setOnClickListener() {
            //clear sharedpreferences
            editor.clear()
            editor.apply()
            finish()
        }
    }
}