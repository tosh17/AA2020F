package ru.thstdio.aa2020.ui.detail

import com.github.terrakok.cicerone.androidx.FragmentScreen

object MoviesDetailsScreen {
    fun getScreen(idCinema: Long) = FragmentScreen {
        FragmentMoviesDetails.newInstance(idCinema)
    }
}