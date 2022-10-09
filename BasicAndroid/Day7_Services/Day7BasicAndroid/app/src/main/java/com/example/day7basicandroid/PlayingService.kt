package com.example.day7basicandroid

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Bundle
import android.os.IBinder
import android.util.Log

class PlayingService : Service() {
    private var binder: IBinder = LocalBinder()
    private var number1: Int? = 0
    private var number2: Int? = 0
    private var bundle: Bundle? = Bundle()

    inner class LocalBinder : Binder() {
        fun getPlayingService(): PlayingService = this@PlayingService
    }

    override fun onBind(intent: Intent): IBinder {
        //nhận 2 số từ fragment gửi qua
        bundle = intent.extras
        number1 = bundle?.getInt("number1")
        number2 = bundle?.getInt("number2")
        Log.d("DHP", "$number1-$number2")
        return binder
    }

    //các funtion tính toán
    fun plus(): Int? {
        return number2?.let { number1?.plus(it) }
    }

    fun minus(): Int? {
        return (number2?.let { number1?.minus(it) })
    }

    fun multi(): Int? {
        return number1?.times(number2!!)
    }

    fun divide(): Float {
        return number1!!.toFloat() / (number2?.toFloat()!!)
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
        Log.d("DHP", "onUnbind")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("DHP", "onDestroy")
    }

}