package com.example.day7basicandroid

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.day7basicandroid.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //broadcast lắng nghe từ service download
    private var onCompleted: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            binding.tvStatus.text = "Download completed"
            Toast.makeText(context, "onCompleted", Toast.LENGTH_LONG).show()
        }
    }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //tạo fragmetn playing
        val dialog = PlayingFragment()
        //đắng ký broadcast với IntentFilter
        registerReceiver(onCompleted, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        binding.btnStartPlaying.setOnClickListener() {
            dialog.show(supportFragmentManager, "playing")

        }
        binding.btnDownload.setOnClickListener() {
            val intent = Intent(this, DownloadService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
        binding.btnCancel.setOnClickListener() {
            stopDownloadService()
        }
    }

    private fun stopDownloadService() {
        binding.tvStatus.text = "click download"
        val serviceIntent = Intent(this, DownloadService::class.java)
        stopService(serviceIntent)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onCompleted)
    }
}