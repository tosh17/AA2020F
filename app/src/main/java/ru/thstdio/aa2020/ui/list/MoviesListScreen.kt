package ru.thstdio.aa2020.ui.list

import com.github.terrakok.cicerone.androidx.FragmentScreen

object MoviesListScreen {
    fun getScreen() = FragmentScreen {
        FragmentMoviesList.newInstance()
    }
}