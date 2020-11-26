package ru.thstdio.aa2020

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : FragmentNavigation(R.layout.fragment_movies_details) {

    private val binding: FragmentMoviesDetailsBinding by viewBinding()

    companion object {
        fun newInstance() = FragmentMoviesDetails()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areaBack.setOnClickListener {
            router?.back()
        }
    }
}