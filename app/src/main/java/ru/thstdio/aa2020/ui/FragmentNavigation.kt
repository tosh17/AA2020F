package ru.thstdio.aa2020.ui

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import ru.thstdio.aa2020.api.Repository

abstract class FragmentNavigation @ContentView constructor(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {
    protected val router: Router get() = CinemaApp.INSTANCE.router
    protected val appRepository: Repository
        get() = CinemaApp.INSTANCE.repository

}