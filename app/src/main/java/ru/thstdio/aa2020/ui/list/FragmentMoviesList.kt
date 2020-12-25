package ru.thstdio.aa2020.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.*
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding
import ru.thstdio.aa2020.ui.FragmentNavigation

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    companion object {
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    private val binding: FragmentMoviesListBinding by viewBinding()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        Log.e("Error", "CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    private var scope = CoroutineScope(
        SupervisorJob() +
                Dispatchers.Main.immediate + exceptionHandler
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.recyclerView.adapter =
            CinemaListAdapter() { cinema -> router.openDetail(cinema.id) }
        loadMoviesList()

    }

    private fun loadMoviesList() {
        scope.launch {
            val appContext = requireActivity().applicationContext
            val repository = Repository(appContext)
            val listMovies = repository.downloadMovies()
            val adapter = binding.recyclerView.adapter as CinemaListAdapter?
            adapter?.setCinemas(listMovies)
        }
    }

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()
    }
}