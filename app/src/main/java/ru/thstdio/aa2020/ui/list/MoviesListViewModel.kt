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
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.data.Cinema

const val DIFF_ITEM_TO_LOAD = 10

class MoviesListViewModel(private val repository: Repository) :
    ViewModel() {


    private var loadNewPageJob: Job? = null
    private val _cinema: MutableLiveData<List<Cinema>> = MutableLiveData()
    val cinemas: LiveData<List<Cinema>> get() = _cinema
    private val _scrollListenerStatus: MutableLiveData<Boolean> = MutableLiveData()
    val scrollListenerStatus: LiveData<Boolean>
        get() = _scrollListenerStatus

    private var isCurrentModeCache = true
    private var currentPage = 1
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }

    init {
        viewModelScope.launch(exceptionHandler) {
            _scrollListenerStatus.value = false
            try {
                _cinema.value = repository.getMoviesFromCache()
            } catch (e: Exception) {
                Log.e("ListCinema", e.message ?: "")
            }
            delay(2000)
            _cinema.value = repository.getMoviesFromPage(1).list
            _scrollListenerStatus.value = true
        }
    }

    fun updateCurrentItemPosition(position: Int) {
        if ((_cinema.value?.size ?: 0) - position < DIFF_ITEM_TO_LOAD
            && loadNewPageJob?.isActive != true
        )
            loadNewPageJob = viewModelScope.launch(exceptionHandler) {
                Log.e("Scroll", "Start new page $currentPage")
                _scrollListenerStatus.value = false
                currentPage++
                val result = repository.getMoviesFromPage(currentPage)
                _cinema.value = _cinema.value?.plus(result.list)
                if (result.totalPage > currentPage) _scrollListenerStatus.value = true
            }
    }
}