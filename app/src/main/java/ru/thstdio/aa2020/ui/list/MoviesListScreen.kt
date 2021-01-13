package ru.thstdio.aa2020.ui.list

import com.github.terrakok.cicerone.androidx.FragmentScreen

class MoviesListScreen : FragmentScreen(fragmentCreator = {
    FragmentMoviesList.newInstance()
})
