package ru.thstdio.aa2020.ui

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import ru.thstdio.aa2020.api.Repository

class CinemaApp : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder: NavigatorHolder
        get() = cicerone.getNavigatorHolder()
    val repository: Repository = Repository()

    companion object {
        internal lateinit var INSTANCE: CinemaApp
            private set
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}