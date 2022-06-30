package dev.falhad.movieshowcase.ui.favorite

import androidx.lifecycle.ViewModel
import dev.falhad.movieshowcase.repository.MovieRepository

class FavoriteViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val favoriteResult = movieRepository.favorites()

}