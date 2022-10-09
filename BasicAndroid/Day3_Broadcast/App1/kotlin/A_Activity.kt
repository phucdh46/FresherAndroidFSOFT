package com.example.basicandroidday3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.basicandroidday3.databinding.ActivityAactivityBinding

class A_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityAactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val list = listOf<String>(
            "Hello!",
            "Hi!",
            "Salut!",
            "Hallo!",
            "Ciao!",
            "Ahoj!",
            "YAH sahs!",
            "Bog!",
            "Hej!",
            "Czesc!",
            "Ní hảo!",
            "Kon’nichiwa!",
            "Annyeonghaseyo!",
            "Shalom!",
            "Sah-wahd-dee-kah!",
            "Merhaba!",
            "Hujambo!",
            "Olá!"
        )
        //start activity B
        binding.btnStartB.setOnClickListener() {
            val i = Intent(this, B_Activity::class.java)
            i.putExtra("data", ArrayList(list))
            startActivity(i)
        }
    }
}