package com.example.basicandroidday3

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class Broadcast : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        //Automatically start MainActivity at device’s power on
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            Log.d("TAG", "Auto Start")
            var i = Intent(context, MainActivity::class.java)
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(i)
        }
    }
}