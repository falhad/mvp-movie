package dev.falhad.movieshowcase.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.falhad.movieshowcase.repository.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {



    lateinit var id: String
    val movie by lazy {
        movieRepository.liveById(id)
    }

    var canShow : Boolean = false

    fun toggleFav() {
      viewModelScope.launch {   movieRepository.toggleFav(id) }
    }

    fun toggleShow() {
        viewModelScope.launch {   movieRepository.toggleShow(id) }
    }

}