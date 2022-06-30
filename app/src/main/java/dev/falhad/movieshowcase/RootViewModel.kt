package dev.falhad.movieshowcase

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dev.falhad.movieshowcase.repository.AuthRepository
import dev.falhad.movieshowcase.repository.MovieRepository

class RootViewModel(
    private val authRepository: AuthRepository,
    private val sharedPreferences: SharedPreferences,
    private val movieRepository: MovieRepository,
) : ViewModel() {


    fun logoutLocally() {
        authRepository.logout()
    }

    val jwt = authRepository.tokenFlow()

    private var appTheme = currentTheme()


    fun currentTheme() = sharedPreferences.getString("theme", "os") ?: "os"

    fun saveTheme(theme: String) {
        with(sharedPreferences.edit()) {
            putString("theme", theme)
            commit()
        }
        appTheme = theme
    }


}