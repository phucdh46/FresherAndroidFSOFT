package com.example.unittest.task3

import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class LogUtils {
    fun setDebugMode(input: Boolean): Boolean {
        if (input) {
            //Log.d("TAG", "Debug Mode enable")
            return false
        } else {
            //Log.d("TAG", "Debug Mode disable")
            return true
        }
    }

}


