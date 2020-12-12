package ru.thstdio.aa2020.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Genre
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.databinding.ViewHolderCinemaBinding

class CinemaListAdapter(
    private val cinemas: List<Movie>,
    private val router: (Movie) -> Unit
) :
    RecyclerView.Adapter<CinemaListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cinema, parent, false)
        return CinemaListHolder(view, router)
    }

    override fun onBindViewHolder(holder: CinemaListHolder, position: Int) {
        holder.onBindView(cinemas[position])
    }

    override fun getItemCount() = cinemas.size
}

class CinemaListHolder(private val view: View, private val onClick: (Movie) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding = ViewHolderCinemaBinding.bind(view)

    fun onBindView(cinema: Movie) {
        setName(cinema.title)
        setGenre(cinema.genres)
        setPoster(cinema.poster)
        setLike(cinema.ratings > 8)
        setRating(cinema.ratings)
        setReviewCount(cinema.numberOfRatings)
        setTime(cinema.runtime)
        setAge(cinema.minimumAge)
        setOnClick { onClick(cinema) }
    }


    private fun setName(name: String) {
        binding.textMovieName.text = name
    }

    private fun setGenre(genre: List<Genre>) {
        binding.textMovieType.text = genre.joinToString { it.name }
    }

    private fun setPoster(poster: String) {
        binding.imageBg.load(poster)

    }

    private fun setAge(age: Int) {
        binding.textAge.text = "$age+"
    }

    private fun setLike(isLike: Boolean) {
        binding.imageLike.setImageResource(if (isLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    private fun setRating(ratingCount: Float) {
        binding.rating.setRating(ratingCount)
    }

    private fun setReviewCount(reviews: Int) {
        binding.textReviews.text =
            binding.textReviews.context.getString(R.string.review_string, reviews)
    }

    private fun setTime(time: Int) {
        binding.textMovieTime.text =
            binding.textMovieTime.context.getString(R.string.time_string, time)
    }

    private fun setOnClick(function: () -> Unit) {
        binding.root.setOnClickListener { function.invoke() }
    }

}