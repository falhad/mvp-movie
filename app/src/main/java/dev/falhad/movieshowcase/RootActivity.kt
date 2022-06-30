package dev.falhad.movieshowcase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import org.koin.androidx.viewmodel.ext.android.viewModel


open class RootActivity : AppCompatActivity() {

    private val rootViewModel by viewModel<RootViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        changeTheme(rootViewModel.currentTheme(), false)
        super.onCreate(savedInstanceState)

    }

    fun changeTheme(theme: String, recreate: Boolean) {

        rootViewModel.saveTheme(theme)

        val newTheme = when (theme) {
            "dark" -> AppCompatDelegate.MODE_NIGHT_YES
            "light" -> AppCompatDelegate.MODE_NIGHT_NO
            else -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        }

        AppCompatDelegate.setDefaultNightMode(newTheme)
        if (recreate) {
            recreate()
        }
    }

}