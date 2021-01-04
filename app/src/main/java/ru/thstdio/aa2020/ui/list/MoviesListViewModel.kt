package ru.thstdio.aa2020.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Cinema


class MoviesListViewModel(repository: Repository) :
    ViewModel() {
    var pagedListLiveData: LiveData<PagedList<Cinema>>
    private val dataSourceFactory: CinemaDataSourceFactory = CinemaDataSourceFactory(
        repository,
        viewModelScope
    )
    private val getMutableLiveData = dataSourceFactory.sourceLiveData

    init {
        pagedListLiveData = dataSourceFactory.toLiveData(pageSize = 20)
    }


}