package dev.falhad.movieshowcase.model.db.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName
import dev.falhad.movieshowcase.model.db.converter.RatingConverter
import dev.falhad.movieshowcase.network.model.Rating

@Entity(
    tableName = "movies"
)

data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = "imdbID") val id: String,

    @ColumnInfo(name = "Actors")
    val actors: String? = null,
    @ColumnInfo(name = "Awards")
    val awards: String? = null,
    @ColumnInfo(name = "BoxOffice")
    val boxOffice: String? = null,
    @ColumnInfo(name = "Country")
    val country: String? = null,
    @ColumnInfo(name = "DVD")
    val dVD: String? = null,
    @ColumnInfo(name = "Director")
    val director: String? = null,
    @ColumnInfo(name = "Genre")
    val genre: String? = null,
    @ColumnInfo(name = "imdbRating")
    val imdbRating: String? = null,
    @ColumnInfo(name = "imdbVotes")
    val imdbVotes: String? = null,
    @ColumnInfo(name = "Language")
    val language: String? = null,
    @ColumnInfo(name = "Metascore")
    val metascore: String? = null,
    @ColumnInfo(name = "Plot")
    val plot: String? = null,
    @ColumnInfo(name = "Poster")
    val poster: String? = null,
    @ColumnInfo(name = "Production")
    val production: String? = null,
    @ColumnInfo(name = "Rated")
    val rated: String? = null,


    @ColumnInfo(name = "Released")
    val released: String? = null,
    @ColumnInfo(name = "Response")
    val response: String? = null,
    @ColumnInfo(name = "Runtime")
    val runtime: String? = null,
    @ColumnInfo(name = "Title")
    val title: String? = null,
    @ColumnInfo(name = "totalSeasons")
    val totalSeasons: String? = null,
    @ColumnInfo(name = "Type")
    val type: String? = null,
    @ColumnInfo(name = "Website")
    val website: String? = null,
    @ColumnInfo(name = "Writer")
    val writer: String? = null,
    @ColumnInfo(name = "Year")
    val year: String? = null,

    @ColumnInfo(name = "Favorite")
    var favorite: Boolean = false,
    @ColumnInfo(name = "CanShow")
    var canShow: Boolean = true


)

fun String?.getIfNotNA(): String? {
    if (this.isNullOrEmpty()) return null
    if (this == "N/A") return null
    return this
}


fun MovieEntity.summaryString() = arrayListOf(
    runtime,
    genre,
    year,
    language
).map { it.getIfNotNA() }
    .joinToString(" • ")


