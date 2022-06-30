package dev.falhad.movieshowcase.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.falhad.movieshowcase.repository.MovieRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    fun logout(){
        viewModelScope.launch {

        }
    }


}