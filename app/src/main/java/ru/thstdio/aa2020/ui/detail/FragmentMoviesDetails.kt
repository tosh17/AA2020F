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
import ru.thstdio.aa2020.api.Repository
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
    private var repository: Repository? = null
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, exception ->
        println("CoroutineExceptionHandler got $exception in $coroutineContext")
    }
    private val scope: CoroutineScope = CoroutineScope(
        Dispatchers.Main.immediate +
                exceptionHandler
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        repository = Repository(requireActivity().applicationContext)
        binding.areaBack.setOnClickListener {
            router.back()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val adapter = ActorAdapter()
        binding.recyclerView.adapter = adapter
        arguments?.let { arg ->
            val cinemaId = arg.getLong(CinemaArg)
            scope.launch {
                repository?.downloadMovie(cinemaId)?.let { cinema ->
                    bindView(cinema)
                }

            }
        }
    }

    private fun bindView(cinema: Movie) {
        binding.textTitle.text = cinema.title
        setBg(cinema.backdrop)
        binding.textTag.text = cinema.genres.joinToString { it.name }
        binding.textReviews.text = getString(R.string.review_string, cinema.numberOfRatings)
        binding.textStory.text = cinema.overview
        binding.rating.setRating(cinema.ratings)
        if (cinema.actors.isNotEmpty()) {
            val adapter = binding.recyclerView.adapter as ActorAdapter?
            adapter?.setActors(cinema.actors)
            adapter?.notifyDataSetChanged()
        } else {
            binding.textCast.isVisible = false
            binding.recyclerView.isVisible = false
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

    override fun onDestroyView() {
        scope.cancel()
        super.onDestroyView()

    }
}