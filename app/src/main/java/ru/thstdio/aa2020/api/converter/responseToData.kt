package ru.thstdio.aa2020.api.converter

import ru.thstdio.aa2020.api.response.ConfigurationResponse


fun createPreviewImgUrl(
    posterPath: String?, backdropPath: String?,
    configuration: ConfigurationResponse
): String =
    when {
        backdropPath != null -> configuration.images.secureBaseURL + configuration.images.backdropSizes.first() + backdropPath
        posterPath != null -> configuration.images.secureBaseURL + configuration.images.posterSizes.first() + posterPath
        else -> ""
    }

fun createOriginalImgUrl(
    posterPath: String?, backdropPath: String?,
    configuration: ConfigurationResponse
): String =
    when {
        backdropPath != null -> configuration.images.secureBaseURL + configuration.images.backdropSizes.last() + backdropPath
        posterPath != null -> configuration.images.secureBaseURL + configuration.images.posterSizes.last() + posterPath
        else -> ""
    }

fun Boolean.adultToAge(): Int = if (this) 18 else 6
