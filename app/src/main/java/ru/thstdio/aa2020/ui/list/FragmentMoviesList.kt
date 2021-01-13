package ru.thstdio.aa2020.ui.list

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding
import ru.thstdio.aa2020.ui.FragmentNavigation
import ru.thstdio.aa2020.ui.detail.MoviesDetailsScreen

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    companion object {
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    private val binding: FragmentMoviesListBinding by viewBinding()
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(appRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        val adapter =
            CinemaListAdapter { cinema -> router.navigateTo(MoviesDetailsScreen(cinema.id)) }
        viewModel.pagedListLiveData.observe(this.viewLifecycleOwner, adapter::submitList)
        binding.recyclerView.adapter = adapter
    }

}