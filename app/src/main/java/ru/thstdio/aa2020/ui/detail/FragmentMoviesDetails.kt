package ru.thstdio.aa2020.ui.detail

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import coil.transform.BlurTransformation
import com.google.android.material.datepicker.MaterialDatePicker
import ru.thstdio.aa2020.R
import ru.thstdio.aa2020.data.Actor
import ru.thstdio.aa2020.data.CinemaDetail
import ru.thstdio.aa2020.databinding.FragmentMoviesDetailsBinding
import ru.thstdio.aa2020.ui.FragmentNavigation
import ru.thstdio.aa2020.ui.view.extension.sendCalendarEvent


class FragmentMoviesDetails : FragmentNavigation(R.layout.fragment_movies_details) {
    companion object {
        private const val CinemaArg = "Cinema_ID"
        fun newInstance(cinema: Long): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val bundle = Bundle(1)
            bundle.putLong(CinemaArg, cinema)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val REQUEST_CODE: Int = 1002
    private val CALENDAR_PERMISSION = Manifest.permission.WRITE_CALENDAR
    private val binding: FragmentMoviesDetailsBinding by viewBinding()
    private val viewModel: MoviesDetailsViewModel by viewModels {
        val cinemaId = arguments?.getLong(CinemaArg)
        MoviesDetailsViewModelFactory(appRepository, cinemaId!!)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.areaBack.setOnClickListener {
            router.exit()
        }
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        val adapter = ActorAdapter()
        binding.recyclerView.adapter = adapter
        viewModel.movieState.observe(this.viewLifecycleOwner, this::bindView)
        binding.addToCalendarButton.setOnClickListener { onClickCalendarBtn() }
    }

    private fun bindView(cinema: CinemaDetail) {
        binding.textTitle.text = cinema.title
        setBg(cinema.backdrop)
        binding.textTag.text = cinema.genres.joinToString { it.name }
        binding.textReviews.text = getString(R.string.review_string, cinema.numberOfRatings)
        binding.textStory.text = cinema.overview
        binding.rating.setRating(cinema.ratings)
        binding.textAge.text = "${cinema.minimumAge}+"
        showActorList(cinema.actors)
    }

    private fun setBg(poster: String) {
        binding.imageBgOrig.load(poster) {
            crossfade(true)
            placeholder(R.drawable.cinema_holder)
            transformations(
                listOf(
                    //GrayscaleTransformation(),
                    BlurTransformation(requireContext(), 5f)
                )
            )
        }
    }

    private fun showActorList(actors: List<Actor>) {
        if (actors.isNotEmpty()) {
            val adapter = binding.recyclerView.adapter as? ActorAdapter
            adapter?.setActors(actors)
        } else {
            binding.textCast.isVisible = false
            binding.recyclerView.isVisible = false
        }
    }

    private fun onClickCalendarBtn() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                CALENDAR_PERMISSION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(arrayOf(CALENDAR_PERMISSION), REQUEST_CODE)
        } else {
            showCalendarsTimeDialog()
        }
    }

    private fun showCalendarsTimeDialog() {
        val builder = MaterialDatePicker.Builder.datePicker()
        val picker = builder.build()
        picker.addOnPositiveButtonClickListener { time ->
            sendEventToCalendar(time)
        }
        picker.show(parentFragmentManager, picker.toString())
    }

    private fun sendEventToCalendar(time: Long) {
        viewModel.movieState.value?.let { cinema ->
            val calID: Long = 1
            val title = cinema.title
            val description = cinema.overview
            requireActivity().sendCalendarEvent(title, description, time, calID)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (permissions.first() == CALENDAR_PERMISSION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                showCalendarsTimeDialog()
            }
        }
    }
}