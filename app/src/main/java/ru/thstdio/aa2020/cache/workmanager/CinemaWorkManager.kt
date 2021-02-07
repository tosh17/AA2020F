package ru.thstdio.aa2020.cache.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

const val WORK_TAG = "CinemaWorkManager_Tag"

class CinemaWorkManager {
    private val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .build()
    private val work = PeriodicWorkRequest.Builder(CinemaWorker::class.java, 15, TimeUnit.SECONDS)
        .setConstraints(constraints)
        .addTag(WORK_TAG)
        .build()

    fun start(context: Context) {
        //WorkManager.getInstance(context).cancelAllWork()
        val currentWork = WorkManager.getInstance(context)
            .getWorkInfosByTag(WORK_TAG).get().firstOrNull()
        currentWork?.let { currentWork ->
            Log.d("Worker", "CurrentWork ${currentWork.id}   ${currentWork.state}")
        }
        if (currentWork == null) {
            WorkManager.getInstance(context).enqueue(work)
            Log.d("Worker", "Start ${work.id}")
        }
    }
}