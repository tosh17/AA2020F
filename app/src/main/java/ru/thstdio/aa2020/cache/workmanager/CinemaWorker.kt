package ru.thstdio.aa2020.cache.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import coil.ImageLoader
import coil.request.ImageRequest
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Mutex
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.ui.CinemaApp
import ru.thstdio.aa2020.ui.view.extension.createIntentWithDeepLink
import java.util.concurrent.atomic.AtomicReference

class CinemaWorker(private val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val NOTIFICATION_ID: Int = 1
    private val CHANNEL_ID: String = "CINEMA_CHANNEL_ID"
    private val repository: Repository
        get() = (applicationContext as CinemaApp).repository
    private val mutex: Mutex = Mutex()

    override suspend fun doWork(): Result =
        coroutineScope {
            Log.d("Worker", "StartWork")
            val ids = repository.getCinemaDetailIDsInCache()
            val positive = 1
            val negative = -1
            var cinemaWithBestRating: AtomicReference<CinemaDetail> = AtomicReference()
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
                createNotification(cinema)
            }
            if ((sum < 0) && (runAttemptCount <= 3)) {
                Result.retry()
            } else {
                Result.success()
            }
        }

    private fun updateIfCinemaRatingBetter(
        cinema: CinemaDetail,
        atomicCinema: AtomicReference<CinemaDetail>
    ) {
        var isDone = false
        while (!isDone) {
            val currentReference = atomicCinema.get()
            isDone = when {
                currentReference == null -> atomicCinema.compareAndSet(currentReference, cinema)
                currentReference.ratings < cinema.ratings -> atomicCinema.compareAndSet(
                    currentReference,
                    cinema
                )
                else -> true
            }
        }
    }


    private suspend fun createNotification(cinema: CinemaDetail) {
        createNotificationChannel(context, CHANNEL_ID)
        Log.d("Worker", "Best Movie ${cinema.title}")
        val resultPendingIntent = context.createIntentWithDeepLink(cinema.id)

        var builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.cinema_holder)
            .setContentTitle(cinema.title)
            .setContentText(cinema.overview)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(resultPendingIntent)
            .setAutoCancel(true)


        val imageLoader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(cinema.backdrop)
            .build()
        val drawable = imageLoader.execute(request).drawable
        val bitmap = (drawable as BitmapDrawable).bitmap
        builder.setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(bitmap)
                .bigLargeIcon(null)
        )

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
    }

}

private fun createNotificationChannel(context: Context, channelId: String) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.channel_name)
        val descriptionText = context.getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        NotificationManagerCompat.from(context).createNotificationChannel(channel)
    }
}