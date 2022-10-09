package com.example.contentproviderclientday10

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contentproviderclientday10.adapter.ReportAdapter
import com.example.contentproviderclientday10.databinding.ActivityMainBinding
import com.example.contentproviderclientday10.vm.ReportViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ReportViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = ReportAdapter()
        binding.rv.adapter = adapter
        //display all reports
        binding.rv.layoutManager = LinearLayoutManager(this)
        viewModel.report.observe(this) { it ->
            adapter.submitData(it)
        }
        //display all reports when swipe refresh
        binding.swipeRefresh.setOnRefreshListener() {
            viewModel.report.observe(this) { it ->
                adapter.submitData(it)
                binding.swipeRefresh.isRefreshing = false
            }
        }
    }
}