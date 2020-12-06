package ru.thstdio.aa2020.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.ViewHolderCinemaBinding
import ru.thstdio.aa2020.dto.CinemaDto

class CinemaListAdapter(
    private val cinemas: List<CinemaDto>,
    private val router: (CinemaDto) -> Unit
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

class CinemaListHolder(private val view: View, private val onClick: (CinemaDto) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding = ViewHolderCinemaBinding.bind(view)

    fun onBindView(cinema: CinemaDto) {
        setName(cinema.name)
        setGenre(cinema.genre)
        setPoster(cinema.posterId)
        setLike(cinema.like)
        setRating(cinema.rating)
        setReviewCount(cinema.reviews)
        setTime(cinema.time)
        setAge(cinema.age)
        setOnClick { onClick(cinema) }
    }

    private fun setName(name: String) {
        binding.textMovieName.text = name
    }

    private fun setGenre(genre: String) {
        binding.textMovieType.text = genre
    }

    private fun setPoster(@DrawableRes resId: Int) {
        binding.imageBg.setImageResource(resId)
    }

    private fun setAge(age: String) {
        binding.textAge.text = age
    }

    private fun setLike(isLike: Boolean) {
        binding.imageLike.setImageResource(if (isLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    private fun setRating(ratingCount: Int) {
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