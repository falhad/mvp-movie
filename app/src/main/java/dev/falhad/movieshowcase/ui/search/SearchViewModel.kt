package dev.falhad.movieshowcase.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.network.Presenter
import dev.falhad.movieshowcase.network.controller.MovieType
import dev.falhad.movieshowcase.repository.MovieRepository
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SearchViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    val searchResult = MutableLiveData<Presenter<List<MovieEntity>>>(Presenter.Loading)
    private val searchTerm = MutableStateFlow("")

    init {

        viewModelScope.launch {
            searchTerm
                .debounce(1200)
                .collectLatest {
                    searchByTerm(it)
                }
        }
    }

    fun search(term: String? = null) {
        searchTerm.value = term?:""
    }

    fun searchByTerm(term: String) {
        println("search $term |> ${System.currentTimeMillis()}")
        viewModelScope.launch {
            searchResult.value = Presenter.Loading
            if (term.isEmpty()) {
                searchResult.value = Presenter.Success(arrayListOf())
                return@launch
            }
            movieRepository.searchByTerm(term = term).fold(
                onSuccess = {
                    searchResult.value = Presenter.Success(it)
                },
                onFailure = {
                    searchResult.value = Presenter.Error(it.message)
                })
        }
    }



}