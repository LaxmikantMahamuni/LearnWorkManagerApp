package com.example.testapp

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class UploadPhotos10PM(context: Context, parameters: WorkerParameters): Worker(context, parameters) {
    override fun doWork(): Result {
        Log.d("WORK_MANAGER", "Uploading Photos...")
        return Result.success()
    }
}