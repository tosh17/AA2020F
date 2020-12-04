package ru.thstdio.aa2020

import ru.thstdio.aa2020.dto.CinemaDto

interface Navigation {
    fun openDetail(cinema: CinemaDto)
    fun back()
}