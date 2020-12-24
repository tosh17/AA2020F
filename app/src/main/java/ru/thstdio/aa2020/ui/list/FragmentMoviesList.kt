package ru.thstdio.aa2020.ui.list

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.api.Repository
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding
import ru.thstdio.aa2020.ui.FragmentNavigation

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    companion object {
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    private val binding: FragmentMoviesListBinding by viewBinding()
    private val appContext: Context
        get() = requireActivity().applicationContext
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(appContext, router)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        viewModel.loadMoviesList()
        viewModel.moviesState.observe(this.viewLifecycleOwner, this::loadMoviesList)
        binding.recyclerView.adapter =
            CinemaListAdapter() { cinema -> router.openDetail(cinema.id) }
        loadMoviesList()

    }

    private fun loadMoviesList(listMovies: List<Movie>) {
        binding.recyclerView.adapter =
            CinemaListAdapter(listMovies) { cinema -> viewModel.openDetail(cinema.id) }
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