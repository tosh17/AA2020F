package ru.thstdio.aa2020.ui.navigation

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import ru.thstdio.aa2020.cache.Repository
import ru.thstdio.aa2020.ui.CinemaApp
import ru.thstdio.aa2020.ui.view.extension.getAppContext

abstract class FragmentNavigation @ContentView constructor(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId), SharedViewFragment {
    private val cinemaApp: CinemaApp get() = getAppContext() as CinemaApp
    protected val router: Router get() = cinemaApp.router
    protected val appRepository: Repository
        get() = cinemaApp.repository

}