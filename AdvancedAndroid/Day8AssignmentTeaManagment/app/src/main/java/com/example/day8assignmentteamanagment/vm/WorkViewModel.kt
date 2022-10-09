package com.example.day8assignmentteamanagment.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.day8assignmentteamanagment.worker.TeaReminderWorker
import java.util.concurrent.TimeUnit

class WorkViewModel(app: Application) : AndroidViewModel(app) {
    private val workManager = WorkManager.getInstance(app.applicationContext)

    fun enqueuePeriodicReminder() {
        //create request with input data
        val request = PeriodicWorkRequestBuilder<TeaReminderWorker>(15, TimeUnit.MINUTES)
            .setInputData(
                workDataOf(
                    "title" to "Reminder",
                    "message" to "Drink tea everyday"
                )
            )
        workManager.enqueueUniquePeriodicWork(
            "SAMPLE",
            ExistingPeriodicWorkPolicy.REPLACE,
            request.build()
        )

    }
}