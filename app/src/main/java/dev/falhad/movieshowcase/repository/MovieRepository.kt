package dev.falhad.movieshowcase.repository

import androidx.lifecycle.LiveData
import dev.falhad.movieshowcase.model.db.dao.MovieDao
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.network.controller.MovieController
import dev.falhad.movieshowcase.network.controller.MovieType
import dev.falhad.movieshowcase.network.model.MovieResponse
import dev.falhad.movieshowcase.network.model.SearchResult
import dev.falhad.movieshowcase.network.model.toMovieEntity
import dev.falhad.movieshowcase.network.parse
import dev.falhad.movieshowcase.utils.API_KEY
import java.lang.Exception

class MovieRepository(
    private val movieController: MovieController,
    private val movieDao: MovieDao
) {


    fun liveById(imdbId: String): LiveData<MovieEntity?> {
        return movieDao.liveByIMDBId(imdbId)
    }

    suspend fun getById(imdbId: String): MovieEntity? {
        return movieDao.getByIMDBId(imdbId)
    }


    suspend fun searchByTerm(
        term: String?,
        year: Int? = null,
        type: MovieType? = null
    ): Result<List<MovieEntity>> {

        try {
            if (term.isNullOrEmpty()) return Result.success(arrayListOf())

            val response =
                movieController.search(apiKey = API_KEY, term = term, year = year, type = type)
            if (response.isSuccessful && response.body() != null) {
                val movies = response.body()?.search ?: arrayListOf()
                movies.filter { !it.imdbID.isNullOrEmpty() }.forEach {
                    if (getById(it.imdbID!!) == null) {
                        fetchMovieByIMDBId(it.imdbID)
                    }
                }

                val result = arrayListOf<MovieEntity>()
                movies.filter { !it.imdbID.isNullOrEmpty() }.forEach {
                    getById(it.imdbID!!)?.let { movieEntity ->
                        if (movieEntity.canShow) {
                            result.add(movieEntity)
                        }
                    }
                }

                return Result.success(result)
            } else {
                return Result.failure(response.parse())
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }

    }


    suspend fun fetchMovieByIMDBId(imdbId: String): Result<MovieResponse> {
        try {
            val response = movieController.byIMDBId(apiKey = API_KEY, imdbId = imdbId)
            val movieResponse = response.body()
            return if (response.isSuccessful && movieResponse != null) {
                movieDao.insertAll(movieResponse.toMovieEntity())
                Result.success(movieResponse)
            } else {
                Result.failure(response.parse())
            }
        }catch (e:Exception){
            return Result.failure(e)
        }

    }

    suspend fun toggleFav(id: String) {
        getById(id)?.let { movieEntity ->
            movieEntity.favorite = !movieEntity.favorite
            movieDao.insertAll(movieEntity)
        }
    }

    suspend fun toggleShow(id: String) {
        getById(id)?.let { movieEntity ->
            movieEntity.canShow = !movieEntity.canShow
            movieDao.insertAll(movieEntity)
        }
    }

    fun favorites() = movieDao.favorites()
    fun hideList() = movieDao.hideList()

}