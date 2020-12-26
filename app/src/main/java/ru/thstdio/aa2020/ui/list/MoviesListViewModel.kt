package ru.thstdio.aa2020.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Movie

class MoviesListViewModel(private val repository: Repository) :
    ViewModel() {
    private val _mutableMoviesState = MutableLiveData<List<Movie>>()
    val moviesState: LiveData<List<Movie>> get() = _mutableMoviesState
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    init {
        viewModelScope.launch(exceptionHandler) {
            _mutableMoviesState.value = repository.downloadMovies()
        }
    }
}