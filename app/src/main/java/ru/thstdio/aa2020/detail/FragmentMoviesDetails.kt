package ru.thstdio.aa2020.detail

import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.thstdio.aa2020.FragmentNavigation
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding
import ru.thstdio.aa2020.dto.CinemaDto
import ru.thstdio.aa2020.dto.loadActorsList
import ru.thstdio.aa2020.dto.loadCinemasList


class FragmentMoviesDetails : FragmentNavigation(R.layout.fragment_movies_details) {

    private val binding: FragmentMoviesDetailsBinding by viewBinding()

    companion object {
        private val CinemaArg = "Cinema_ID"
        fun newInstance(cinema: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            fragment.arguments = bundleOf(CinemaArg to cinema)
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areaBack.setOnClickListener {
            router?.back()
        }
        arguments?.let {
            val actorId = it[CinemaArg] as Int
            bindView(loadCinemasList()[actorId])
        }
    }

    fun bindView(cinema: CinemaDto) {
        binding.textTitle.text = cinema.name
        setBg(cinema.posterId)
        binding.textTag.text = cinema.genre
        binding.textReviews.text = getString(R.string.review_string, cinema.reviews)
        setRating(cinema.rating)
        binding.recyclerView.run {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = DetailMoviesAdapter(loadActorsList())
        }
    }

    private fun setBg(posterId: Int) {
        binding.imageBgOrig.setImageResource(posterId)
        val matrix = ColorMatrix()
        matrix.setSaturation(0f)

        val filter = ColorMatrixColorFilter(matrix)
        binding.imageBgOrig.setColorFilter(filter)
    }

    private fun setRating(rating: Int) {
        val active = ContextCompat.getColor(requireContext(), R.color.star_enable)
        val disable = ContextCompat.getColor(requireContext(), R.color.star_disable)
        val getColor: (Int) -> Int = { if (it <= rating) active else disable }
        binding.imageStar1.setColorFilter(getColor(1))
        binding.imageStar2.setColorFilter(getColor(2))
        binding.imageStar3.setColorFilter(getColor(3))
        binding.imageStar4.setColorFilter(getColor(4))
        binding.imageStar5.setColorFilter(getColor(5))
    }
}