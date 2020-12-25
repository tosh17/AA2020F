package ru.thstdio.aa2020.ui.list

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Movie
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
        binding.recyclerView.adapter =
            CinemaListAdapter() { cinema -> viewModel.openDetail(cinema.id) }
        viewModel.loadMoviesList()
        viewModel.moviesState.observe(this.viewLifecycleOwner, this::loadMoviesList)
    }

    private fun loadMoviesList(listMovies: List<Movie>) {
        val adapter = binding.recyclerView.adapter as CinemaListAdapter?
        adapter?.setCinemas(listMovies)
    }
}