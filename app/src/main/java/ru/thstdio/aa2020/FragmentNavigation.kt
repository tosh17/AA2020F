package ru.thstdio.aa2020

import androidx.annotation.ContentView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class FragmentNavigation @ContentView constructor(@LayoutRes contentLayoutId: Int) :
    Fragment(contentLayoutId) {
    protected val router: Navigation?
        get() = (requireActivity() as Navigation)

}