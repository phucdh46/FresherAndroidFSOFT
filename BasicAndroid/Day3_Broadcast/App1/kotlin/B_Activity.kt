package com.example.basicandroidday3

import android.content.ComponentName
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.example.basicandroidday3.databinding.ActivityBactivityBinding

class B_Activity : AppCompatActivity() {
    companion object {
        private const val MY_ACTION = "com.example.dhp"
    }

    private lateinit var binding: ActivityBactivityBinding
    lateinit var broadcast: Broadcast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //display list item on TextView in ListView and Log
        val list = intent.getStringArrayListExtra("data")
        Log.d("DHP", list.toString())
        binding.lv.adapter =
            ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, ArrayList(list))

        // start another application use startActivity with intent
        binding.btnStartAppIntent.setOnClickListener() {
            val intent = Intent()
            val component = ComponentName(
                "com.example.custombroadcast2",
                "com.example.custombroadcast2.MainActivity"
            )
            intent.setComponent(component)
            startActivity(intent)
        }

        broadcast = Broadcast()
        val intentFilter = IntentFilter(MY_ACTION)
        registerReceiver(broadcast, intentFilter)
        //start another application use BroadcastReceiver
        binding.btnStartAppBroadcast.setOnClickListener() {
            val intent = Intent(MY_ACTION)
            sendBroadcast(intent)
        }
    }

    /*override fun onStop() {
        super.onStop()
        unregisterReceiver(broadcast)
    }*/

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcast)
    }
}