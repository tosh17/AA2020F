package ru.thstdio.aa2020.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.ViewHolderActorBinding
import ru.thstdio.aa2020.dto.ActorDto

class DetailMoviesAdapter(private val actors: List<ActorDto>) :
    RecyclerView.Adapter<DetailMoviesHolderImpl>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailMoviesHolderImpl {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        val binding = ViewHolderActorBinding.bind(view)
        return DetailMoviesHolderImpl(binding)
    }

    override fun onBindViewHolder(holder: DetailMoviesHolderImpl, position: Int) {
        val actor = actors[position]
        holder.setName(actor.name)
        holder.setImg(actor.imgId)
    }

    override fun getItemCount() = actors.size
}