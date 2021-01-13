package ru.thstdio.aa2020.ui.detail

import com.github.terrakok.cicerone.androidx.FragmentScreen

class MoviesDetailsScreen(idCinema: Long) :
    FragmentScreen(fragmentCreator = { FragmentMoviesDetails.newInstance(idCinema) }) {
}