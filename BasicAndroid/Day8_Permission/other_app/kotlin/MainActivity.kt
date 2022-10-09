package com.example.other_app

import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.other_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val CUSTOM_PERMISSION_CODE = 101
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnCustom = findViewById<Button>(R.id.btnCustom)
        val intent = Intent()
        val componentName = ComponentName("com.example.basicandroidday8","com.example.basicandroidday8.MainActivity")
        intent.setComponent(componentName)
        this.sendOrderedBroadcast(
            intent,
            "com.example.MY_PERMISSION",
            object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    // getResultCode();
                    btnCustom.setOnClickListener() {
                        checkPermission(
                            "com.example.MY_PERMISSION",
                            CUSTOM_PERMISSION_CODE
                        )
                    }
                }
            },
            null,
            RESULT_CANCELED,
            null,
            null
        )
    }

    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                permission
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CUSTOM_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this@MainActivity, "Custom Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@MainActivity, "Custom Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
}