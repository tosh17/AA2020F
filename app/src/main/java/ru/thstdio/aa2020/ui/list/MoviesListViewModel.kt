package ru.thstdio.aa2020.ui.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.thstdio.aa2020.cache.ITEMS_SIZE_IN_PAGE
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.data.Cinema


class MoviesListViewModel(private val repository: Repository) :
    ViewModel() {


    private var loadNewPageJob: Job? = null
    private val _cinemas: MutableLiveData<List<Cinema>> = MutableLiveData()
    val cinemas: LiveData<List<Cinema>> get() = _cinemas
    private val _scrollListenerStatus: MutableLiveData<Boolean> = MutableLiveData(false)
    val scrollListenerStatus: LiveData<Boolean>
        get() = _scrollListenerStatus

    private var currentPage = 1
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        loadNewPageJob = viewModelScope.launch(exceptionHandler) {
            try {
                _cinemas.value = repository.getCinemasFromCache()
            } catch (e: Exception) {
                Log.e("ListCinema", e.message ?: "")
            }
            delay(2000)
            getMovies(isFirstPage = true)
        }
    }

    fun findLastVisibleItemPosition(visibleItemPosition: Int) {
        if ((_cinemas.value?.size ?: 0) - visibleItemPosition < ITEMS_SIZE_IN_PAGE / 2
            && loadNewPageJob?.isActive != true
        ) {
            getMovies()
        }
    }

    private fun getMovies(isFirstPage: Boolean = false) {
        loadNewPageJob = viewModelScope.launch(exceptionHandler) {
            _scrollListenerStatus.value = false
            val result = repository.getCinemasFromPage(currentPage++)
            _cinemas.value = if (isFirstPage) {
                result.list
            } else {
                _cinemas.value?.plus(result.list)
            }
            _scrollListenerStatus.value = (result.totalPage > currentPage)
        }
    }
}