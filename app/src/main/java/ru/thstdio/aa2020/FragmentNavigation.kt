package ru.thstdio.aa2020

import androidx.fragment.app.Fragment

abstract class FragmentNavigation : Fragment() {
    protected val router: Navigation?
        get() {
            return (requireActivity() as Navigation)
        }
}