package dev.falhad.movieshowcase.ui.hidden

import androidx.lifecycle.ViewModel
import dev.falhad.movieshowcase.repository.MovieRepository

class HiddenViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val hiddenResult = movieRepository.hideList()

}