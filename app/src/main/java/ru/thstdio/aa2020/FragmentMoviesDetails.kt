package ru.thstdio.aa2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : Fragment(), HasNavigation {
    var router: Navigation? = null
    private lateinit var binding: FragmentMoviesDetailsBinding

    companion object {
        fun newInstance() = FragmentMoviesDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)
        binding = FragmentMoviesDetailsBinding.bind(view)
        binding.textBack.setOnClickListener {
            router?.back()
        }
        return view

    }

    override fun setNavigation(navigation: Navigation) {
        router = navigation
    }
}