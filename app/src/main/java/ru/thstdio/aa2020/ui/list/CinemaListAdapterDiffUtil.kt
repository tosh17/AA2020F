package ru.thstdio.aa2020.ui.list

import androidx.recyclerview.widget.DiffUtil
import ru.thstdio.aa2020.data.Cinema

class CinemaListAdapterDiffUtil : DiffUtil.ItemCallback<Cinema>() {
    override fun areItemsTheSame(
        oldCinema: Cinema,
        newCinema: Cinema
    ) = oldCinema.id == newCinema.id

    override fun areContentsTheSame(
        oldCinema: Cinema,
        newCinema: Cinema
    ) = oldCinema == newCinema
}
