package ru.thstdio.aa2020.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.ui.Navigation

class MoviesDetailsViewModel(private val repository: Repository, private val router: Navigation) :
    ViewModel() {
    private val _mutableMovieState = MutableLiveData<Movie>()
    val movieState: LiveData<Movie> get() = _mutableMovieState

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    fun loadCinemaDetail(idCinema: Long) {
        viewModelScope.launch(exceptionHandler) {
            try {
                _mutableMovieState.value = repository.downloadMovie(idCinema)
            } catch (e: Exception) {
            }
        }
    }

    fun onRouteBack() {
        router.back()
    }
}