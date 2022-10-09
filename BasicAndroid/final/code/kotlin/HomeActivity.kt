package com.example.finalbasicandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.finalbasicandroid.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //start MainActivity by an implicit Intent
        val intent = Intent("com.example.MainAction")
        binding.btnStartMainActivity.setOnClickListener(){
            startActivity(intent)
        }
    }
}