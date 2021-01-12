package ru.thstdio.aa2020.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.navigation.Screen

class MainActivity : AppCompatActivity() {
    private val navigator = AppNavigator(this, R.id.fragment_container_view)
    private val navigatorHolder: NavigatorHolder get() = CinemaApp.INSTANCE.navigatorHolder
    private val router: Router get() = CinemaApp.INSTANCE.router

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            router.navigateTo(Screen.ListCinema())
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }
}