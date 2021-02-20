package ru.thstdio.aa2020.ui.view.extension

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import coil.ImageLoader
import coil.request.ImageRequest
import ru.thstdio.aa2020.R

import ru.thstdio.aa2020.data.CinemaDetail

private const val NOTIFICATION_ID: Int = 1
private const val CHANNEL_ID: String = "CINEMA_CHANNEL_ID"

suspend fun Context.createNotificationBestCinema(cinema: CinemaDetail) {
    createCinemaNotificationChannel()
    Log.d("Worker", "Best Movie ${cinema.title}")
    val resultPendingIntent = this.createIntentWithCinemaDeepLink(cinema.id)

    var builder = NotificationCompat.Builder(this, CHANNEL_ID)
        .setSmallIcon(R.drawable.cinema_holder)
        .setContentTitle(cinema.title)
        .setContentText(cinema.overview)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(resultPendingIntent)
        .setAutoCancel(true)


    val imageLoader = ImageLoader(this)
    val request = ImageRequest.Builder(this)
        .data(cinema.backdrop)
        .build()
    val drawable = imageLoader.execute(request).drawable
    val bitmap = (drawable as BitmapDrawable).bitmap
    builder.setStyle(
        NotificationCompat.BigPictureStyle()
            .bigPicture(bitmap)
            .bigLargeIcon(null)
    )

    NotificationManagerCompat.from(this).notify(NOTIFICATION_ID, builder.build())
}

private fun Context.createCinemaNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = getString(R.string.channel_name)
        val descriptionText = getString(R.string.channel_description)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system
        NotificationManagerCompat.from(this).createNotificationChannel(channel)
    }
}