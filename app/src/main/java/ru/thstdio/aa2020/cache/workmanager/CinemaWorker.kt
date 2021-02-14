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
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.ui.CinemaApp
import ru.thstdio.aa2020.ui.view.extension.createNotificationBestCinema
import java.util.concurrent.atomic.AtomicReference


class CinemaWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {


    private val repository: Repository
        get() = (applicationContext as CinemaApp).repository

    override suspend fun doWork(): Result =
        coroutineScope {
            Log.d("Worker", "StartWork")
            val ids = repository.getCinemaDetailIDsInCache()
            val positive = 1
            val negative = -1
            val cinemaWithBestRating: AtomicReference<CinemaDetail?> = AtomicReference()
            val deferreds: List<Deferred<Int>> = ids.map { id ->
                async {
                    try {
                        val cinema = repository.getCinemaDetail(id)
                        updateIfCinemaRatingBetter(cinema, cinemaWithBestRating)
                        positive
                    } catch (e: Exception) {
                        negative
                    }
                }
            }
            val sum = deferreds.awaitAll().sum()
            cinemaWithBestRating.get()?.let { cinema ->
                context.createNotificationBestCinema(cinema)
            }
            if ((sum < 0) && (runAttemptCount <= 3)) {
                Result.retry()
            } else {
                Result.success()
            }
        }

    private fun updateIfCinemaRatingBetter(
        cinema: CinemaDetail,
        atomicCinema: AtomicReference<CinemaDetail?>
    ) {
        var isDone = false
        while (!isDone) {
            val currentReference = atomicCinema.get()
            isDone =
                if ((currentReference == null) || (currentReference.ratings < cinema.ratings)) {
                    atomicCinema.compareAndSet(currentReference, cinema)
                } else {
                    true
                }
        }
    }

}

