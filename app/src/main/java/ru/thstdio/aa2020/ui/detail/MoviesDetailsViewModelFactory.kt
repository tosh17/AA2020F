package ru.thstdio.aa2020.ui.detail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.ui.Navigation


class MoviesDetailsViewModelFactory(private val context: Context, private val router: Navigation) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesDetailsViewModel::class.java -> MoviesDetailsViewModel(Repository(context), router)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}