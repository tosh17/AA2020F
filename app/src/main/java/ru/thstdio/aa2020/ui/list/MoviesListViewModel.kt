package ru.thstdio.aa2020.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Cinema


class MoviesListViewModel(private val repository: Repository) :
    ViewModel() {
    var pagedListLiveData: LiveData<PagedList<Cinema>> = createPagedListLiveData()


    private fun createPagedListLiveData(): LiveData<PagedList<Cinema>> {
        val dataSourceFactory: CinemaDataSourceFactory = CinemaDataSourceFactory(
            repository,
            viewModelScope
        )
        return dataSourceFactory.toLiveData(pageSize = 20)
    }
}