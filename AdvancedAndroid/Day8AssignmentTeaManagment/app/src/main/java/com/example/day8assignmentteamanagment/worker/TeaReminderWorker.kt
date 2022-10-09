package com.example.day8assignmentteamanagment.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.day8assignmentteamanagment.util.NotificationHelper

class TeaReminderWorker(val context: Context, val params: WorkerParameters) :
    Worker(context, params) {

    override fun doWork(): Result {
        //set notification with title and message
        NotificationHelper(context).createNotification(
            inputData.getString("title").toString(),
            inputData.getString("message").toString()
        )
        return Result.success()
    }
}