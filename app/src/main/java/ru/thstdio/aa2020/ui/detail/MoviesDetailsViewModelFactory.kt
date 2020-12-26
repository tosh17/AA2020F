package ru.thstdio.aa2020.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.aa2020.api.Repository


class MoviesDetailsViewModelFactory(private val appContext: Context, private val cinemaId: Long) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(
            Repository(appContext),
            cinemaId
        )
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}