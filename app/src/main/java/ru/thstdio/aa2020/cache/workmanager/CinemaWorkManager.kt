package ru.thstdio.aa2020.cache.workmanager

import android.content.Context
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

class CinemaWorkManager {
    private val constraints = Constraints.Builder()
        .setRequiresCharging(true)
        .setRequiredNetworkType(NetworkType.UNMETERED)
        .build()
    private val work = PeriodicWorkRequest.Builder(CinemaWorker::class.java, 8, TimeUnit.HOURS)
        .setConstraints(constraints)
        .build()

    fun start(context: Context) {
        WorkManager.getInstance(context).enqueue(work)
    }
}