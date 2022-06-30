package dev.falhad.movieshowcase.network.model

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("Response")  val response: String?=null,
    @SerializedName("Search")  val search: List<SearchResult>? = null,
    @SerializedName("totalResults")  val totalResults: String?=null
)