package ru.thstdio.aa2020.ui.list

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.transition.MaterialContainerTransform
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.databinding.FragmentMoviesListBinding
import ru.thstdio.aa2020.ui.detail.MoviesDetailsScreen
import ru.thstdio.aa2020.ui.navigation.FragmentNavigation
import java.lang.ref.WeakReference

class FragmentMoviesList : FragmentNavigation(R.layout.fragment_movies_list) {
    companion object {
        fun newInstance(): FragmentMoviesList = FragmentMoviesList()
    }

    private val binding: FragmentMoviesListBinding by viewBinding()
    private val viewModel: MoviesListViewModel by viewModels {
        MoviesListViewModelFactory(appRepository)
    }
    var sharedView: WeakReference<View?> = WeakReference(null)
    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (dy > 0) {
                val position =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                viewModel.findLastVisibleItemPosition(position)
            }
            super.onScrolled(recyclerView, dx, dy)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform()
        sharedElementReturnTransition = MaterialContainerTransform()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        val adapter =
            CinemaListAdapter { cinema, view ->
                sharedView = WeakReference(view)
                router.navigateTo(MoviesDetailsScreen(cinema.id))
            }
        viewModel.cinemas.observe(this.viewLifecycleOwner, adapter::submitList)
        viewModel.scrollListenerStatus.observe(
            this.viewLifecycleOwner,
            this::addRemoveScrollListener
        )
        binding.recyclerView.adapter = adapter
    }

    private fun addRemoveScrollListener(isAdd: Boolean) {
        Log.e("addRemoveScrollListener", isAdd.toString())
        if (isAdd) {
            binding.recyclerView.addOnScrollListener(scrollListener)
        } else {
            binding.recyclerView.removeOnScrollListener(scrollListener)
        }
    }

    override fun onDestroyView() {
        binding.recyclerView.removeOnScrollListener(scrollListener)
        super.onDestroyView()
    }

    override fun getSharedView(): View? {
        return sharedView.get()
    }
}