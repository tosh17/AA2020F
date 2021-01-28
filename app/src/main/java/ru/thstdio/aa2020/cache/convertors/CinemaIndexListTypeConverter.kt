package ru.thstdio.aa2020.cache.convertors

import androidx.room.TypeConverter

private const val LIST_SEPARATOR = ","

class CinemaIndexListTypeConverter {
    @TypeConverter
    fun fromList(list: List<Long>): String = list.joinToString(separator = LIST_SEPARATOR)

    @TypeConverter
    fun toList(str: String): List<Long> = str.split(LIST_SEPARATOR).mapNotNull { it.toLong() }

}