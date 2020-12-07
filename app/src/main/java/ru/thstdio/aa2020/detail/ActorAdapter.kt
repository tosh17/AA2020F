package ru.thstdio.aa2020.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.ViewHolderActorBinding
import ru.thstdio.aa2020.dto.ActorDto

class ActorAdapter(private val actors: List<ActorDto>) :
    RecyclerView.Adapter<ActorHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_actor, parent, false)
        return ActorHolder(view)
    }

    override fun onBindViewHolder(holder: ActorHolder, position: Int) {
        holder.onBindView(actors[position])
    }

    override fun getItemCount() = actors.size
}

class ActorHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ViewHolderActorBinding.bind(view)
    fun onBindView(actor: ActorDto) {
        setName(actor.name)
        setImg(actor.imgId)
    }

    private fun setImg(@DrawableRes resId: Int) {
        binding.imageMovie.setImageResource(resId)
    }

    private fun setName(name: String) {
        binding.textMove.text = name
    }
}