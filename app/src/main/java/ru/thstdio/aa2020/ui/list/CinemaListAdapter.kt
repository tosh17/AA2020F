package ru.thstdio.aa2020.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Movie
import ru.thstdio.aa2020.databinding.ViewHolderCinemaBinding

class CinemaListAdapter(
    private val router: (Movie) -> Unit
) :
    RecyclerView.Adapter<CinemaListHolder>() {
    private var cinemas: List<Movie> = listOf()
    fun setCinemas(cinemas: List<Movie>) {
        this.cinemas = cinemas
        notifyDataSetChanged()
    }

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

    @SuppressLint("SetTextI18n")
    fun onBindView(cinema: Movie) {
        binding.textMovieName.text = cinema.title
        binding.textMovieType.text = cinema.genres.joinToString { it.name }
        binding.imageBg.load(cinema.poster)
        binding.textAge.text = "${cinema.minimumAge}+"
        setLike(cinema.ratings > 8)
        binding.rating.setRating(cinema.ratings)
        binding.textReviews.text =
            binding.textReviews.context.getString(R.string.review_string, cinema.numberOfRatings)
        binding.textMovieTime.text =
            binding.textMovieTime.context.getString(R.string.time_string, cinema.runtime)
        binding.root.setOnClickListener { onClick(cinema) }
    }

    private fun setLike(isLike: Boolean) {
        binding.imageLike.setImageResource(if (isLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }


}