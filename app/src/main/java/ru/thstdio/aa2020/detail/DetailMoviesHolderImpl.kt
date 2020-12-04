package ru.thstdio.aa2020.detail

import androidx.recyclerview.widget.RecyclerView
import ru.thstdio.aa2020.databinding.ViewHolderActorBinding

class DetailMoviesHolderImpl(val view: ViewHolderActorBinding) : RecyclerView.ViewHolder(view.root),
    DetailMoviesHolder {
    override fun setImg(resId: Int) {
        view.imageMovie.setImageResource(resId)
    }

    override fun setName(name: String) {
        view.textMove.text = name
    }
}