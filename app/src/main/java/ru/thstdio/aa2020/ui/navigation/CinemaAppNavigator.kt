package ru.thstdio.aa2020.ui.navigation

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.aa2020.ui.detail.FragmentMoviesDetails
import ru.thstdio.aa2020.ui.list.FragmentMoviesList

class CinemaAppNavigator(activity: FragmentActivity, containerId: Int) :
    AppNavigator(activity, containerId) {
    override fun setupFragmentTransaction(
        fragmentTransaction: FragmentTransaction,
        currentFragment: Fragment?,
        nextFragment: Fragment?
    ) {

        if (currentFragment is FragmentMoviesList && nextFragment is FragmentMoviesDetails) {
            setupFragmentTransactionFromCinemaToDetail(
                currentFragment,
                nextFragment,
                fragmentTransaction
            )
        }

    }

    private fun setupFragmentTransactionFromCinemaToDetail(
        currentFragment: FragmentMoviesList,
        nextFragment: FragmentMoviesDetails,
        fragmentTransaction: FragmentTransaction
    ) {
        val sharedView = currentFragment.getSharedView()
        Log.d("Navigation", "${sharedView}")
        sharedView?.let { view ->
            fragmentTransaction.addSharedElement(view, "shared_cinema_container")
        }
    }


}