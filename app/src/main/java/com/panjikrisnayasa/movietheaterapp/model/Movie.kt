package com.panjikrisnayasa.movietheaterapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Movie(
        var id: String? = null,
        var poster: String? = null,
        var title: String? = null,
        var voteAverage: String? = null,
        var voteCount: String? = null,
        var rating: Boolean? = null,
        var genre: String? = null,
        var release: String? = null,
        var runtime: String? = null,
        var production: String? = null,
        var overview: String? = null
) : Parcelable