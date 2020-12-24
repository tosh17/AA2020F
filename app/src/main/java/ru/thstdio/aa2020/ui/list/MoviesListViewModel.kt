package ru.thstdio.aa2020.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.thstdio.aa2020.api.util.Repository
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.ui.Navigation

class MoviesListViewModel(private val repository: Repository, val router: Navigation) :
    ViewModel() {
    private val _mutableMoviesState = MutableLiveData<List<Movie>>()
    val moviesState: LiveData<List<Movie>> get() = _mutableMoviesState

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    fun loadMoviesList() {
        viewModelScope.launch(exceptionHandler) {
            _mutableMoviesState.value = repository.downloadMovies()

        }
    }

    fun openDetail(id: Long) {
        router.openDetail(id)
    }
}