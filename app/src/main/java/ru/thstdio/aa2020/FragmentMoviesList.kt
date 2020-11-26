package ru.thstdio.aa2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding

class FragmentMoviesList : Fragment(), HasNavigation {
    var router: Navigation? = null
    private lateinit var binding: FragmentMoviesListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        binding = FragmentMoviesListBinding.bind(view)
        binding.movieItem.setOnClickListener {
            router?.openDetail()
        }
        return view
    }

    override fun setNavigation(navigation: Navigation) {
        router = navigation
    }
}