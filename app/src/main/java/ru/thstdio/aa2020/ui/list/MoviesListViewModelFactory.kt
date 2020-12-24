package ru.thstdio.aa2020.ui.list

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.thstdio.aa2020.api.util.Repository
import ru.thstdio.aa2020.ui.Navigation

class MoviesListViewModelFactory(private val context: Context, private val router: Navigation) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when (modelClass) {
        MoviesListViewModel::class.java -> MoviesListViewModel(Repository(context), router)
        else -> throw IllegalArgumentException("$modelClass is not registered ViewModel")
    } as T
}