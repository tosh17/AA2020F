package ru.thstdio.aa2020.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Cinema


class CinemaDataSourceFactory(
    private val repository: Repository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Cinema>() {
    private var source: DataSource<Int, Cinema>? = null
    var sourceLiveData: MutableLiveData<CinemaDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Cinema> {
        source = CinemaDataSource(repository, scope)
        sourceLiveData.postValue(source as CinemaDataSource)
        return source as CinemaDataSource
    }

}