package com.example.basicandroidday2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RetrieveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrieve)
        val intent = Intent().putExtra("data","Retrieve Activity")
        setResult(RESULT_OK,intent)
    }
}