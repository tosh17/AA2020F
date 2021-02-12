package ru.thstdio.aa2020.cache.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
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
import kotlinx.coroutines.sync.withLock
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.ui.CinemaApp
import ru.thstdio.aa2020.ui.MainActivity

class CinemaWorker(val context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {
    private val BASE_DEEP_LINK_URL = "https://www.themoviedb.org/movie/"
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
            var cinemaWithBestRating: CinemaDetail? = null
            val compareCinema: suspend (CinemaDetail) -> Unit = { cinema ->
                mutex.withLock {
                    if (cinemaWithBestRating == null) {
                        cinemaWithBestRating = cinema
                    } else if (cinemaWithBestRating!!.ratings < cinema.ratings) {
                        cinemaWithBestRating = cinema
                    }
                }
            }
            val deferreds: List<Deferred<Int>> = ids.map { id ->
                async {
                    try {
                        val cinema = repository.getCinemaDetail(id)
                        compareCinema(cinema)
                        positive
                    } catch (e: Exception) {
                        negative
                    }
                }
            }
            val sum = deferreds.awaitAll().sum()
            cinemaWithBestRating?.let { cinema ->
                createNotificationChannel()
                createNotification(cinema)
            }
            if ((sum < 0) && (runAttemptCount <= 3)) {
                Result.retry()
            } else {
                Result.success()
            }
        }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.channel_name)
            val descriptionText = context.getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private suspend fun createNotification(cinema: CinemaDetail) {
        Log.d("Worker", "Best Movie ${cinema.title}")
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.data = Uri.parse(BASE_DEEP_LINK_URL + cinema.id)
        val resultPendingIntent = PendingIntent.getActivity(
            context, 0,
            resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )

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
        with(NotificationManagerCompat.from(context)) {
            notify(NOTIFICATION_ID, builder.build())
        }
    }

}
