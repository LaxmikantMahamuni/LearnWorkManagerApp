package com.example.testapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.Calendar
import java.util.concurrent.TimeUnit

class WorkManagerActivity2 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.call_button).setOnClickListener {
            Toast.makeText(this, "Start WorkManager", Toast.LENGTH_SHORT).show()
            scheduleDailyUpload()
        }
    }

    private fun scheduleDailyUpload() {
        val constraint = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        val dueDate = Calendar.getInstance()
        val currentDate = Calendar.getInstance()
        dueDate.set(Calendar.HOUR_OF_DAY, 22)
        dueDate.set(Calendar.MINUTE, 0)
        dueDate.set(Calendar.SECOND, 0)

        if (dueDate.before(currentDate)) {
            // If 10 PM already passed today, schedule for next day
            dueDate.add(Calendar.DAY_OF_MONTH, 1)
        }

        val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
        val initialDelay = timeDiff

        val starterRequest = OneTimeWorkRequestBuilder<StarterWorker>()
            .setConstraints(constraint)
            .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
            .build()

        Log.d("WORK_MANAGER", "Enqueue starter Work Request")
        WorkManager.getInstance(this)
            .enqueueUniqueWork( "10PM Today/Tomorrow",
                ExistingWorkPolicy.REPLACE,
                starterRequest)
    }
}