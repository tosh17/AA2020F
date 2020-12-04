package ru.thstdio.aa2020.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.aa2020.Navigation
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.ViewHolderCinemaBinding
import ru.thstdio.aa2020.dto.CinemaDto

class CinemaListAdapter(val cinemas: List<CinemaDto>, val router: Navigation) :
    RecyclerView.Adapter<CinemaListHolderImpl>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaListHolderImpl {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_cinema, parent, false)
        val binding = ViewHolderCinemaBinding.bind(view)
        return CinemaListHolderImpl(binding)
    }

    override fun onBindViewHolder(holder: CinemaListHolderImpl, position: Int) {
        val actor = cinemas[position]
        holder.setName(actor.name)
        holder.setGenre(actor.genre)
        holder.setPoster(actor.posterId)
        holder.setLike(actor.like)
        holder.setRating(actor.rating)
        holder.setReviewCount(actor.reviews)
        holder.setTime(actor.time)
        holder.setAge(actor.age)
        holder.setOnClick { router.openDetail(actor) }
    }

    override fun getItemCount() = cinemas.size
}