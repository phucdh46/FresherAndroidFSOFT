package com.example.custombroadcast2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class BroacastClass: BroadcastReceiver() {
    companion object {
        private const val MY_ACTION = "com.example.dhp"
    }
    override fun onReceive(context: Context?, intent: Intent?) {
        if (MY_ACTION.equals(intent?.action)){
            context?.startActivity(Intent(context,MainActivity::class.java))
        }
    }
}