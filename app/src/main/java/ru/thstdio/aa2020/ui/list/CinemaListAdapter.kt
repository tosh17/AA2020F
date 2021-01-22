package ru.thstdio.aa2020.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.api.converter.adultToAge
import ru.thstdio.aa2020.data.Cinema
import ru.thstdio.aa2020.databinding.ViewHolderCinemaBinding

class CinemaListAdapter(
    private val router: (Cinema) -> Unit
) :
    RecyclerView.Adapter<CinemaListHolder>() {

    private var list: List<Cinema> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaListHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cinema, parent, false)
        return CinemaListHolder(view, router)
    }

    override fun onBindViewHolder(holder: CinemaListHolder, position: Int) {
        list[position].let { cinema ->
            holder.onBindView(cinema)
        }
    }

    override fun getItemCount(): Int = list.size

    fun notifyData(newList: List<Cinema>) {
        val calculateDiff = DiffUtil.calculateDiff(CinemaListAdapterDiffUtil(list, newList))
        list = newList
        calculateDiff.dispatchUpdatesTo(this)
    }
}

class CinemaListHolder(view: View, private val onClick: (Cinema) -> Unit) :
    RecyclerView.ViewHolder(view) {
    private val binding = ViewHolderCinemaBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun onBindView(cinema: Cinema) {
        binding.textMovieName.text = cinema.title
        binding.textMovieType.text = cinema.genres.joinToString { it.name }
        binding.imageBg.load(cinema.poster) {
            placeholder(R.drawable.ic_film)
            error(R.drawable.ic_film)
        }
        binding.textAge.text = "${cinema.adult.adultToAge()}+"
        setLike(cinema.ratings > 8)
        binding.rating.setRating(cinema.ratings)
        binding.textReviews.text =
            binding.textReviews.context.getString(R.string.review_string, cinema.numberOfRatings)
        binding.root.setOnClickListener { onClick(cinema) }
    }

    private fun setLike(isLike: Boolean) {
        binding.imageLike.setImageResource(if (isLike) R.drawable.ic_like_on else R.drawable.ic_like_off)
    }


}

private class CinemaListAdapterDiffUtil(val oldList: List<Cinema>, val newList: List<Cinema>) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition] == newList[newItemPosition]
}