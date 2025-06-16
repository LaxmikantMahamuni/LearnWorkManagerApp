package com.example.testapp

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class StarterWorker(context: Context, parameters: WorkerParameters) : Worker(context, parameters) {
    override fun doWork(): Result {
        Log.d("WORK_MANAGER_2", "Starting periodic upload schedule...")

        val constraint = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val periodicRequest = PeriodicWorkRequestBuilder<UploadPhotos10PM>(
            24, TimeUnit.HOURS
        ).setConstraints(constraint)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "DailyPhotosUpload",
                            ExistingPeriodicWorkPolicy.UPDATE,
                            periodicRequest
        )

        return Result.success()
    }
}