package dev.falhad.movieshowcase.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.network.Presenter
import dev.falhad.movieshowcase.network.controller.MovieType
import dev.falhad.movieshowcase.repository.MovieRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val trending = MutableLiveData<Presenter<List<MovieEntity>>>(Presenter.Loading)

    val favorites by lazy {
        movieRepository.favorites()
    }

    fun fetchTrending() {
        viewModelScope.launch {
            trending.value = Presenter.Loading
            movieRepository.searchByTerm(term = "the a", year = 2022, type = MovieType.movie).fold(
                onSuccess = {
                    trending.value = Presenter.Success(it)
                },
                onFailure = {
                    trending.value = Presenter.Error(it.message)
                })
        }
    }


}