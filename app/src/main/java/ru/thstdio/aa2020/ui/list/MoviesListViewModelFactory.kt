package ru.thstdio.aa2020.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.aa2020.api.Repository

class MoviesListViewModelFactory(private val appContext: Context) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(Repository(appContext))
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}