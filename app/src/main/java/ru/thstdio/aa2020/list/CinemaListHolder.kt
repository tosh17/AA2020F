package ru.thstdio.aa2020.list

interface CinemaListHolder {
    fun setName(name: String)
    fun setGenre(genre: String)
    fun setPoster(resId: Int)
    fun setAge(age: String)
    fun setLike(isLike: Boolean)
    fun setRating(ratingCount: Int)
    fun setReviewCount(reviews: Int)
    fun setTime(time: Int)
    fun setOnClick(function: () -> Unit)
}