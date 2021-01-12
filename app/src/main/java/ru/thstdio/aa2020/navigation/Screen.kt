package ru.thstdio.aa2020.navigation

import com.github.terrakok.cicerone.androidx.FragmentScreen
import ru.thstdio.aa2020.ui.detail.FragmentMoviesDetails
import ru.thstdio.aa2020.ui.list.FragmentMoviesList

object Screen {
    fun ListCinema() = FragmentScreen(key = ScreenKey.ListCinema.name) {
        FragmentMoviesList.newInstance()
    }

    fun DetailsCinema(idCinema: Long) =
        FragmentScreen(key = ScreenKey.DetailsCinema.name) {
            FragmentMoviesDetails.newInstance(
                idCinema
            )
        }
}

enum class ScreenKey {
    ListCinema, DetailsCinema
}