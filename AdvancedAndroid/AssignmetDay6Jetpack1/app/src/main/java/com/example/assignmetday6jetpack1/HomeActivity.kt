package com.example.assignmetday6jetpack1

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.assignmetday6jetpack1.databinding.ActivityHomeBinding
import com.example.assignmetday6jetpack1.vm.UserViewModel

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.users.observe(this) {
            binding.tvHome.text = it.toString()
        }
    }
}