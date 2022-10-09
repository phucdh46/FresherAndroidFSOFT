package com.example.day6animation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //start fragment A: list song
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<FragmentA>(R.id.fragment)
        }
    }
}