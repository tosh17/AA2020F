package ru.thstdio.aa2020.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.databinding.ViewHolderActorBinding

class ActorAdapter(private val actors: List<Actor>) :
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
    fun onBindView(actor: Actor) {
        setName(actor.name)
        setImg(actor.picture)
    }

    private fun setImg(picture: String) {
        binding.imageMovie.load(picture) {
            crossfade(true)
            transformations(
                RoundedCornersTransformation(25f, 25f, 16f, 16f)
            )
        }
    }

    private fun setName(name: String) {
        binding.textMove.text = name
    }
}