package ru.thstdio.aa2020.ui

import android.app.Application
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.cache.workmanager.CinemaWorkManager

class CinemaApp : Application() {
    private val cicerone = Cicerone.create()
    val router get() = cicerone.router
    val navigatorHolder: NavigatorHolder
        get() = cicerone.getNavigatorHolder()
    val repository: Repository by lazy { Repository(this) }
    override fun onCreate() {
        super.onCreate()
        CinemaWorkManager().start(applicationContext)
    }
}