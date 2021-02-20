package ru.thstdio.aa2020.ui.view.extension

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import ru.thstdio.aa2020.ui.MainActivity

private const val BASE_DEEP_LINK_URL = "https://www.themoviedb.org/movie/"
private const val REQUEST_CODE = 1
fun Context.createIntentWithCinemaDeepLink(cinemaId: Long): PendingIntent {
    val intent = Intent(this, MainActivity::class.java)
    intent.data = Uri.parse(BASE_DEEP_LINK_URL + cinemaId)
    return PendingIntent.getActivity(
        this,
        REQUEST_CODE,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT
    )
}