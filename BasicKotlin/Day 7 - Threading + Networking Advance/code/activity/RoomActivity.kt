package com.example.myapplication.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MainViewModel
import com.example.myapplication.User
import com.example.myapplication.UsersAdapter
import com.example.myapplication.databinding.ActivityRoomBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRoomBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var adapter: UsersAdapter
        //get all users from Room database
        binding.btnStart.setOnClickListener(){
            lifecycleScope.launch( Dispatchers.Main) {
                mainViewModel.getUsers().collect(){
                    adapter = UsersAdapter(it)
                    Log.i("TAG","$it")
                    binding.rv.adapter = adapter
                }
            }
            binding.rv.layoutManager = LinearLayoutManager(this)
        }

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //add random firtname and lastname
        binding.btnAdd.setOnClickListener(){
            var firstName = (1..9).random()
            var lastName = (('a'..'z')) .random()
            Log.i("TAG","$firstName -- $lastName")
            mainViewModel.addUser(User( "$firstName","$lastName"))
        }
    }
}