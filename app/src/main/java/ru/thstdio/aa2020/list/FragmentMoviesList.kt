package ru.thstdio.aa2020.list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.FragmentNavigation
import ru.thstdio.aa2020.Navigation
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding
import ru.thstdio.aa2020.dto.loadCinemasList

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    private val binding: FragmentMoviesListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.run {
            layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
            adapter = CinemaListAdapter(loadCinemasList(), requireActivity() as Navigation)

        }
    }
}