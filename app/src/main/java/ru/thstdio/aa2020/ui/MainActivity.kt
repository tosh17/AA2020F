package ru.thstdio.aa2020.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.cache.workmanager.CinemaWorkManager
import ru.thstdio.aa2020.ui.list.MoviesListScreen

class MainActivity : AppCompatActivity() {
    private val cinemaApp: CinemaApp get() = application as CinemaApp
    private val navigatorHolder: NavigatorHolder get() = cinemaApp.navigatorHolder
    private val router: Router get() = cinemaApp.router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.navigateTo(MoviesListScreen())
            CinemaWorkManager.start(applicationContext)
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(AppNavigator(this, R.id.fragment_container_view))
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}