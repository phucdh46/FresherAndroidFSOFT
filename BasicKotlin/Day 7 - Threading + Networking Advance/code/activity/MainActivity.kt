package com.example.myapplication.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnFlow.setOnClickListener(){
            startActivity(Intent(this, FlowActivity::class.java))
        }
        //start coroutine
        binding.btnStart.setOnClickListener() {
            val job = Job()
            val scope = CoroutineScope(job + Dispatchers.Default)
            var i = 0
            var j = 0
            //job 1 random number 1
            val job1 = scope.launch {
                while (true) {
                    delay(100)
                    i = (1..100).random()
                    withContext(Dispatchers.Main){
                        binding.tv1.text = i.toString()
                    }
                }
            }
            //job 2 random number 2
            val job2 = scope.launch {
                while (true) {
                    delay(100)
                    j = (1..100).random()
                    withContext(Dispatchers.Main){
                        binding.tv2.text = j.toString()
                    }
                }
            }


            //cancel job 1
            binding.tv1.setOnClickListener(){
                job1.cancel()
                binding.tv1.text = "$i"
                binding.tv2.text = "$j"
            }
            //cancel job 2
            binding.tv2.setOnClickListener() {
                job2.cancel()
                binding.tv1.text = "$i"
                binding.tv2.text = "$j"
            }
            // sum = number 1 + number 2
            binding.btnSum.setOnClickListener(){
                job.cancel()
                lifecycleScope.launch(){
                    val one = async { i }
                    val two = async { j }
                    val sum = one.await() + two.await()
                    withContext(Dispatchers.Main) {
                        binding.tvSum.text = "$sum"
                    }
                    Log.i("TAG","The answer is ${one.await() + two.await()}")
                }
            }
        }
    }
}

