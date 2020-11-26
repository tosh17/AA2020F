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
                .apply {
                    add(R.id.fragments_container, listFragment)
                    commit()
                }
        }

    }

    override fun openDetail() {
        val fragment = FragmentMoviesDetails.newInstance()
        supportFragmentManager.beginTransaction()
            .apply {
                add(R.id.fragments_container, fragment, fragment.javaClass.name)
                addToBackStack(fragment.javaClass.name)
                commit()
            }
    }

    override fun back() {
        supportFragmentManager.popBackStack()
    }

}