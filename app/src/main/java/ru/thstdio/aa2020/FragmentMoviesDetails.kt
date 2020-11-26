package ru.thstdio.aa2020

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : FragmentNavigation() {

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
        binding.areaBack.setOnClickListener {
            router?.back()
        }
        return view

    }


}