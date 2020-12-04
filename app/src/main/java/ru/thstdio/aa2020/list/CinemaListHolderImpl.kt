package ru.thstdio.aa2020.list


import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.aa2020.R

import ru.thstdio.aa2020.databinding.ViewHolderCinemaBinding

class CinemaListHolderImpl(val view: ViewHolderCinemaBinding) : RecyclerView.ViewHolder(view.root),
    CinemaListHolder {
    private val context
        get() = view.root.context

    override fun setName(name: String) {
        view.textMovieName.text = name
    }

    override fun setGenre(genre: String) {
        view.textMovieType.text = genre
    }

    override fun setPoster(resId: Int) {
        view.imageBg.setImageResource(resId)
    }

    override fun setAge(age: String) {
        view.textAge.text = age
    }

    override fun setLike(isLike: Boolean) {
        view.imageLike.setImageResource(if (isLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }

    override fun setRating(ratingCount: Int) {
        val active = ContextCompat.getColor(context, R.color.star_enable)
        val disable = ContextCompat.getColor(context, R.color.star_disable)
        val getColor: (Int) -> Int = { if (it <= ratingCount) active else disable }
        view.imageStar1.setColorFilter(getColor(1))
        view.imageStar2.setColorFilter(getColor(2))
        view.imageStar3.setColorFilter(getColor(3))
        view.imageStar4.setColorFilter(getColor(4))
        view.imageStar5.setColorFilter(getColor(5))
    }

    override fun setReviewCount(reviews: Int) {
        view.textReviews.text = context.getString(R.string.review_string, reviews)
    }

    override fun setTime(time: Int) {
        view.textMovieTime.text = context.getString(R.string.time_string, time)
    }

    override fun setOnClick(function: () -> Unit) {
        view.root.setOnClickListener { function.invoke() }
    }
}