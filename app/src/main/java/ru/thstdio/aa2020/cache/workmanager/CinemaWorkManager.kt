package ru.thstdio.aa2020.cache.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit


object CinemaWorkManager {
    private const val WORK_TAG = "CinemaWorkManager_Tag"


    fun start(context: Context) {
        val currentWork = WorkManager.getInstance(context)
            .getWorkInfosByTag(WORK_TAG).get().firstOrNull()
        if (currentWork != null) {
            Log.d("Worker", "CurrentWork ${currentWork.id}   ${currentWork.state}")
        } else {
            val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build()
            val work = PeriodicWorkRequest.Builder(CinemaWorker::class.java, 8, TimeUnit.HOURS)
                .setConstraints(constraints)
                .addTag(WORK_TAG)
                .build()
            WorkManager.getInstance(context).enqueue(work)
            Log.d("Worker", "Start ${work.id}")
        }
    }
}