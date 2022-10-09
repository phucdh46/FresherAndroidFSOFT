package com.example.custombroadcast2

import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    companion object {
        private const val MY_ACTION = "com.example.dhp"
        private const val KEY = "com.example.key"
    }
    lateinit var broacast: BroacastClass
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       /*  broacast = BroacastClass()
        val i = IntentFilter(MY_ACTION)
        registerReceiver(broacast,i)*/
    }

    /*override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broacast)
    }*/
}