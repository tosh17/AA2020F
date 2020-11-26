package ru.thstdio.aa2020

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    private val binding: FragmentMoviesListBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.movieItem.setOnClickListener {
            router?.openDetail()
        }
    }
}