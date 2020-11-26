package ru.thstdio.aa2020

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), Navigation {

    private val listFragment = FragmentMoviesList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, listFragment)
                .commit()
        }
    }

    override fun openDetail() {
        val fragment = FragmentMoviesDetails.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container_view, fragment, fragment.javaClass.name)
            .addToBackStack(fragment.javaClass.name)
            .commit()
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }

}