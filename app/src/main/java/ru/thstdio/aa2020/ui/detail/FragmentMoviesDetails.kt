package ru.thstdio.aa2020.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.BlurTransformation
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding
import ru.thstdio.aa2020.ui.FragmentNavigation


class FragmentMoviesDetails : FragmentNavigation(R.layout.fragment_movies_details) {
    companion object {
        private const val CinemaArg = "Cinema_ID"
        fun newInstance(cinema: Long): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val bundle = Bundle(1)
            bundle.putLong(CinemaArg, cinema)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val binding: FragmentMoviesDetailsBinding by viewBinding()
    private val appContext: Context
        get() = requireActivity().applicationContext
    private val viewModel: MoviesDetailsViewModel by viewModels {
        MoviesDetailsViewModelFactory(appContext, router)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areaBack.setOnClickListener {
            viewModel.onRouteBack()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val adapter = ActorAdapter()
        binding.recyclerView.adapter = adapter
        arguments?.let { arg ->
            val cinemaId = arg.getLong(CinemaArg)
            viewModel.loadCinemaDetail(cinemaId)
        }
        viewModel.movieState.observe(this.viewLifecycleOwner, this::bindView)
    }

    private fun bindView(cinema: Movie) {
        binding.textTitle.text = cinema.title
        setBg(cinema.backdrop)
        binding.textTag.text = cinema.genres.joinToString { it.name }
        binding.textReviews.text = getString(R.string.review_string, cinema.numberOfRatings)
        binding.textStory.text = cinema.overview
        binding.rating.setRating(cinema.ratings)
        binding.textAge.text = "${cinema.minimumAge}+"
        showActorList(cinema.actors)
    }

    private fun setBg(poster: String) {
        binding.imageBgOrig.load(poster) {
            crossfade(true)
            placeholder(R.drawable.cinema_holder)
            transformations(
                listOf(
                    //GrayscaleTransformation(),
                    BlurTransformation(requireContext(), 5f)
                )
            )
        }
    }

    private fun showActorList(actors: List<Actor>) {
        if (actors.isNotEmpty()) {
            val adapter = binding.recyclerView.adapter as ActorAdapter?
            adapter?.setActors(actors)
        } else {
            binding.textCast.isVisible = false
            binding.recyclerView.isVisible = false
        }
    }
}