package ru.thstdio.aa2020.cache.convertors

import androidx.room.TypeConverter

class CinemaIndexListTypeConverter {
    @TypeConverter
    fun fromList(list: List<Long>): String {
        return list.joinToString(separator = ",")
    }

    @TypeConverter
    fun toList(str: String): List<Long> {
        return try {
            str.split(",").map { it.toLong() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}