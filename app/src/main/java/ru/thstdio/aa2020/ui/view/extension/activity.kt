package ru.thstdio.aa2020.ui.view.extension

import android.app.Activity
import android.content.ContentValues
import ru.thstdio.aa2020.data.CinemaDetail
import java.util.*

fun Activity.sendCalendarEvent(cinema: CinemaDetail, time: Long, calID: Long) {
    val title = cinema.title
    val description = cinema.overview
    val values = ContentValues().apply {
        put(android.provider.CalendarContract.Events.DTSTART, time)
        put(android.provider.CalendarContract.Events.DTEND, time)
        put(android.provider.CalendarContract.Events.TITLE, title)
        put(android.provider.CalendarContract.Events.DESCRIPTION, description)
        put(android.provider.CalendarContract.Events.CALENDAR_ID, calID)
        put(
            android.provider.CalendarContract.Events.EVENT_TIMEZONE,
            TimeZone.getDefault().displayName
        )
    }
    this.contentResolver.insert(android.provider.CalendarContract.Events.CONTENT_URI, values)
}