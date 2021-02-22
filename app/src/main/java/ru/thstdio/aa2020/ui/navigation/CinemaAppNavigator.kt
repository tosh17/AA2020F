package ru.thstdio.aa2020.ui.navigation

import android.util.Log
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.aa2020.ui.detail.FragmentMoviesDetails
import ru.thstdio.aa2020.ui.list.FragmentMoviesList

const val SHARED_VIEW_CINEMA = "shared_cinema_container"
const val SHARED_VIEW_CINEMA_DETAIL = "shared_cinema_container_detail"

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
            ViewCompat.setTransitionName(view, SHARED_VIEW_CINEMA_DETAIL)
            fragmentTransaction.addSharedElement(view, SHARED_VIEW_CINEMA)
        }
    }


}