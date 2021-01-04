package ru.thstdio.aa2020.ui

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import ru.thstdio.aa2020.api.Repository

abstract class FragmentNavigation @ContentView constructor(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {
    protected val router: Navigation
        get() = (requireActivity() as Navigation)
    protected val appRepository: Repository
        get() = CinemaApp.repository

}