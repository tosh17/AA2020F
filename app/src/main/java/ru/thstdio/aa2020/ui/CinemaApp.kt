package ru.thstdio.aa2020.ui

import android.app.Application
import ru.thstdio.aa2020.api.Repository

class CinemaApp : Application() {
    companion object {
        val repository: Repository = Repository()
    }
}