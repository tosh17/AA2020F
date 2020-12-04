package ru.thstdio.aa2020.dto

import ru.thstdio.aa2020.R

fun loadCinemasList() = listOf(
    CinemaDto(
        id = 1,
        name = "Avengers: End Game",
        genre = "Action, Adventure, Drama",
        age = "13+",
        posterId = R.drawable.id1_preview,
        time = 137,
        rating = 4,
        like = false,
        reviews = 127
    ),
    CinemaDto(
        id = 2,
        name = "Tenet",
        genre = "Action, Sci-Fi, Thriller",
        age = "16+",
        posterId = R.drawable.id2_preview,
        time = 97,
        rating = 5,
        like = true,
        reviews = 98
    ), CinemaDto(
        id = 3,
        name = "Black Widow",
        genre = "Action, Adventure, Sci-Fi",
        age = "13+",
        posterId = R.drawable.id3_preview,
        time = 102,
        rating = 4,
        like = false,
        reviews = 38
    ), CinemaDto(
        id = 4,
        name = "Wonder Woman 1984",
        genre = "Action, Adventure, Fantasy",
        age = "13+",
        posterId = R.drawable.id4_preview,
        time = 120,
        rating = 5,
        like = false,
        reviews = 74
    )
)

fun loadActorsList() = listOf(
    ActorDto(name = "Robert Downey Jr.", imgId = R.drawable.movie1),
    ActorDto(name = "Chris Evans", imgId = R.drawable.movie2),
    ActorDto(name = "Mark Ruffalo", imgId = R.drawable.movie3),
    ActorDto(name = "Chris Hemsworth", imgId = R.drawable.movie4)
)