package ru.thstdio.aa2020.cache.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.ui.CinemaApp

class CinemaWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val repository: Repository
        get() = (applicationContext as CinemaApp).repository

    override suspend fun doWork(): Result {
        Log.e("Worker", "StartWork")
        val ids = repository.getCinemaDetailIDsInCache()
        coroutineScope {
            for (id in ids) {
                async { repository.getCinemaDetail(id) }
            }
        }
        return Result.success()
    }

}
