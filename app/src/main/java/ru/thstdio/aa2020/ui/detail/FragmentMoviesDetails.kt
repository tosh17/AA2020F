package ru.thstdio.aa2020.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.BlurTransformation
import kotlinx.coroutines.*
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.data.loadMovie
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding
import ru.thstdio.aa2020.ui.FragmentNavigation


class FragmentMoviesDetails : FragmentNavigation(R.layout.fragment_movies_details) {

    private val binding: FragmentMoviesDetailsBinding by viewBinding()
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    private var scope = CoroutineScope(
        Job() +
                Dispatchers.IO +
                exceptionHandler
    )

    companion object {
        private const val CinemaArg = "Cinema_ID"
        fun newInstance(cinema: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val bundle = Bundle()
            bundle.putInt(CinemaArg, cinema)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areaBack.setOnClickListener {
            router?.back()
        }
        arguments?.let { arg ->
            val cinemaId = arg.getInt(CinemaArg)
            scope.launch {
                val cinema = loadMovie(requireContext(), cinemaId)
                withContext(Dispatchers.Main) { bindView(cinema) }
            }
        }
    }

    private fun bindView(cinema: Movie) {
        binding.textTitle.text = cinema.title
        setBg(cinema.backdrop)
        binding.textTag.text = cinema.genres.joinToString { it.name }
        binding.textReviews.text = getString(R.string.review_string, cinema.numberOfRatings)
        binding.textStory.text = cinema.overview
        setRating(cinema.ratings.toInt() / 2)
        if (cinema.actors.isNotEmpty()) {
            binding.recyclerView.layoutManager =
                LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            binding.recyclerView.adapter = ActorAdapter(cinema.actors)
        } else {
            binding.textCast.isVisible = false
        }
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

    private fun setRating(rating: Int) {
        binding.rating.setRating(rating)
    }
}