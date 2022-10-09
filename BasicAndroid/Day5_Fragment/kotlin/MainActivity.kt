package com.example.assignmentday5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignmentday5.databinding.ActivityMainBinding
import java.io.Serializable

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //create list card
        var listCard = ArrayList<Card>()
        listCard = ArrayList()
        listCard.add(Card("Nguyen Van A", 123457, "24/7"))
        listCard.add(Card("Nguyen Van B", 333333, "22/7"))
        //set layout manager for recycleview
        binding.rv.layoutManager = LinearLayoutManager(this)
        // set recycleview adapter
        val adapter = CardAdapter(listCard, this)
        binding.rv.adapter = adapter
        //set Item click event
        adapter.setonItemClickListener(object : CardAdapter.onItemClickListener {
            override fun onItemCLick(position: Int) {
                val card = listCard.get(position)
                Log.d("DHP", "$position")
                val i = Intent(this@MainActivity, CardInforActivity::class.java)
                //send card to DetailActivity
                i.putExtra("data", card as Serializable)
                startActivity(i)
            }
        })
    }
}
