package ru.thstdio.aa2020.ui.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.*
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.loadMovies
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding
import ru.thstdio.aa2020.ui.FragmentNavigation

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    private val binding: FragmentMoviesListBinding by viewBinding()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    private var scope = CoroutineScope(
        Job() +
                Dispatchers.IO +
                exceptionHandler
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        loadMoviesList()

    }

    private fun loadMoviesList() {
        scope.launch {
            val listMovies = loadMovies(requireContext())
            withContext(Dispatchers.Main) {
                binding.recyclerView.adapter =
                    CinemaListAdapter(listMovies) { cinema -> router.openDetail(cinema.id) }
            }
        }
    }
}