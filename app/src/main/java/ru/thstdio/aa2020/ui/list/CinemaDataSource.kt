package ru.thstdio.aa2020.ui.list

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Cinema

class CinemaDataSource(private val repository: Repository, private val scope: CoroutineScope) :
    PageKeyedDataSource<Int, Cinema>() {
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.e("CinemaDataSource", "Error ${exception.message}")
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Cinema>
    ) {
        scope.launch(exceptionHandler) {
            val (list, total) = repository.downloadMovies(1)
            callback.onResult(list, null, if (total == 1) null else 2)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Cinema>) {

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Cinema>) {
        scope.launch(exceptionHandler) {
            val (list, total) = repository.downloadMovies(params.key)
            callback.onResult(list, if (total == params.key) null else params.key + 1)
        }
    }
}