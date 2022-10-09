package com.example.demolaunchmode

import android.app.ActivityManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.example.demolaunchmode.databinding.ActivityBasedBinding

open class BasedActivity(private val activityName: String) : AppCompatActivity() {

    private lateinit var binding: ActivityBasedBinding
    private val TAG = "Tracking"
    private lateinit var activityManage: ActivityManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasedBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        Log.d(TAG, "OnCreate: $activityName")
        //log information
        logData()

        //create activity
        binding.buttonA.setOnClickListener {
            startActivity(Intent(this, AStandardActivity::class.java))
        }

        binding.buttonB.setOnClickListener {
            startActivity(Intent(this, BStandardActivity::class.java))
        }

        binding.buttonC.setOnClickListener {
            startActivity(Intent(this, CStandardActivity::class.java))
        }


        binding.buttonStopA.setOnClickListener {
            startActivity(Intent(this, ASingleTopActivity::class.java))
        }

        binding.buttonStopB.setOnClickListener {
            startActivity(Intent(this, BSingleTopActivity::class.java))
        }

        binding.buttonStopC.setOnClickListener {
            startActivity(Intent(this, CSingleTopActivity::class.java))
        }

        binding.buttonStaskA.setOnClickListener {
            startActivity(Intent(this, ASingleTaskActivity::class.java))
        }

        binding.buttonStaskB.setOnClickListener {
            startActivity(Intent(this, BSingleTaskActivity::class.java))
        }

        binding.buttonStaskC.setOnClickListener {
            startActivity(Intent(this, CSingleTaskActivity::class.java))
        }

        binding.buttonSinstanceA.setOnClickListener {
            startActivity(Intent(this, ASingleInstanceActivity::class.java))
        }

        binding.buttonSinstanceB.setOnClickListener {
            startActivity(Intent(this, BSingleInstanceActivity::class.java))
        }

        binding.buttonSinstanceC.setOnClickListener {
            startActivity(Intent(this, CSingleInstanceActivity::class.java))
        }
    }

    private fun logData() {
        activityManage = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        //get number task
        Log.d(TAG, "number of tasks: ${activityManage.getRunningTasks(10).size}")
        val tasks = activityManage.getRunningTasks(10)
        for (i in 0 until tasks.size) {
            //get number of activity in task
            Log.d(TAG, "number of activity task ${i + 1} : ${tasks[i].numActivities}")
        }
        Log.d(TAG, "-------------------------------------------")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d(TAG, "OnNewIntent: $activityName")
        logData()
    }
}