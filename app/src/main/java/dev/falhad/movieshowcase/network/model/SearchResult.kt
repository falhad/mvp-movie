package dev.falhad.movieshowcase.network.model

import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("Poster") val poster: String? = null,
    @SerializedName("Title") val title: String? = null,
    @SerializedName("Type") val type: String? = null,
    @SerializedName("Year") val year: String? = null,
    @SerializedName("imdbID") val imdbID: String? = null,
)