package ru.thstdio.aa2020.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.data.CinemaDetail

class MoviesDetailsViewModel(private val repository: Repository, private val cinemaId: Long) :
    ViewModel() {
    private val _mutableMovieState = MutableLiveData<CinemaDetail>()
    val movieState: LiveData<CinemaDetail> get() = _mutableMovieState

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            try {
                _mutableMovieState.value = repository.getCinemasDetailFromCache(cinemaId)
            } catch (e: Exception) {
            }
            _mutableMovieState.value = repository.getCinemaDetail(cinemaId)
        }
    }

}