package ru.thstdio.aa2020.cache.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.ui.CinemaApp

class CinemaWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val repository: Repository
        get() = (applicationContext as CinemaApp).repository

    override suspend fun doWork(): Result =
        coroutineScope {
            Log.d("Worker", "StartWork")
            val ids = repository.getCinemaDetailIDsInCache()
            val positive = 1
            val negative = -1

            val deferreds: List<Deferred<Int>> = ids.map { id ->
                async {
                    try {
                        repository.getCinemaDetail(id)
                        positive
                    } catch (e: Exception) {
                        negative
                    }
                }
            }
            val sum = deferreds.awaitAll().sum()
            if ((sum < 0) && (runAttemptCount <= 3)) {
                Result.retry()
            } else {
                Result.success()
            }
        }

}
