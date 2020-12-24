package ru.thstdio.aa2020.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.ui.detail.FragmentMoviesDetails
import ru.thstdio.aa2020.ui.list.FragmentMoviesList

class MainActivity : AppCompatActivity(), Navigation {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, FragmentMoviesList.newInstance())
                .commit()
        }
    }

    override fun openDetail(id: Long) {
        val fragment = FragmentMoviesDetails.newInstance(id)
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }

}