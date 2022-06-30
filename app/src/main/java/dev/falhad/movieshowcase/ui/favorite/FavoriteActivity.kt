package dev.falhad.movieshowcase.ui.favorite

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.adapter.MovieListAdapter
import dev.falhad.movieshowcase.adapter.MovieListMode
import dev.falhad.movieshowcase.databinding.ActivityMainBinding
import dev.falhad.movieshowcase.databinding.ActivityFavoriteBinding
import dev.falhad.movieshowcase.network.Presenter
import dev.falhad.movieshowcase.ui.home.HomeViewModel
import dev.falhad.movieshowcase.utils.openMovie
import dev.falhad.movieshowcase.utils.showSnackBar
import dev.falhad.movieshowcase.utils.toArrayList
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    private val favoriteViewModel by viewModel<FavoriteViewModel>()

    private val favoriteAdapter by lazy {
        MovieListAdapter(onItemClicked = {
            openMovie(it)
        }, onFavClicked = {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener { onBackPressed() }

        favoriteAdapter.changeMode(MovieListMode.ROW)
        binding.favoriteResultRv.layoutManager = LinearLayoutManager(this)
        binding.favoriteResultRv.adapter = favoriteAdapter


        favoriteViewModel.favoriteResult.observe(this) { presenter ->
            if (presenter.isNotEmpty()){
                favoriteAdapter.update(presenter.toArrayList())
            }else{
                //show there is no favorite in list layout
                showSnackBar(getString(R.string.there_is_no_favorite_in_this_list))
            }
        }

    }



}