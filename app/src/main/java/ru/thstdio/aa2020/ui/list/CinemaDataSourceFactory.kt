package ru.thstdio.aa2020.ui.list

import androidx.paging.DataSource
import kotlinx.coroutines.CoroutineScope
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.data.Cinema

class CinemaDataSourceFactory(
    private val repository: Repository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Cinema>() {
    override fun create(): DataSource<Int, Cinema> = CinemaDataSource(repository, scope)
}