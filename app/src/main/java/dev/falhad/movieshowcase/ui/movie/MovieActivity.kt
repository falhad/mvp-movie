package dev.falhad.movieshowcase.ui.movie

import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.adapter.MovieListAdapter
import dev.falhad.movieshowcase.databinding.ActivityMovieBinding
import dev.falhad.movieshowcase.model.db.entity.getIfNotNA
import dev.falhad.movieshowcase.model.db.entity.summaryString
import dev.falhad.movieshowcase.utils.loadImage
import dev.falhad.movieshowcase.utils.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieBinding

    private val movieViewModel by viewModel<MovieViewModel>()

    private val searchAdapter by lazy {
        MovieListAdapter(onItemClicked = {}, onFavClicked = {})
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener { onBackPressed() }
        intent.extras?.getString("id")?.let {
            movieViewModel.id = it
        } ?: kotlin.run {
            showSnackBar(getString(R.string.invalid_movie_id))
            finish()
        }

        movieViewModel.movie.observe(this) {

            it?.let { movie ->
                val (
                    id, actors, awards, boxOffice, country,
                    dVD, director, genre, imdbRating, imdbVotes,
                    language, metascore, plot, poster, production,
                    rated, released, response, runtime, title,
                    totalSeasons, type, website, writer,
                    year, favorite, canShow
                ) = movie


                binding.cover.loadImage(poster)
                binding.title.text = title
                binding.rate.text = imdbRating.getIfNotNA() ?: "-"
                binding.rateCount.text = " | ${imdbVotes.getIfNotNA() ?: "-"}"
                binding.summary.text = movie.summaryString()
                binding.plot.text = movie.plot.getIfNotNA()

                updateFav(favorite)
                updateCanShow(canShow)

            }

        }

        binding.share.setOnClickListener {
             showSnackBar(getString(R.string.feature_not_implemented))
        }

        binding.fav.setOnClickListener { movieViewModel.toggleFav() }
        binding.menu.setOnClickListener {
            val popupMenu = PopupMenu(this, it)

            val canShow = movieViewModel.canShow
            when (canShow) {
                true -> popupMenu.menu.add(getString(R.string.hide_from_future_searches))
                false -> popupMenu.menu.add(getString(R.string.show_in_future_search))
            }
            popupMenu.setOnMenuItemClickListener {
                movieViewModel.toggleShow()
                val msg = when (canShow) {
                    true -> R.string.hide_from_future_searches_msg
                    false -> R.string.show_in_future_search_msg
                }
                showSnackBar(getString(msg))
//                finish()
                true
            }
            popupMenu.show()
        }

    }

    private fun updateCanShow(canShow: Boolean?) {
        movieViewModel.canShow = canShow ?: true
    }

    private fun updateFav(favorite: Boolean?) {
        if (favorite == true) {
            binding.fav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_24
                )
            )
            binding.fav.setColorFilter(ContextCompat.getColor(this, R.color.red))
        } else {
            binding.fav.setImageDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.ic_baseline_favorite_border_24
                )
            )
            binding.fav.setColorFilter(ContextCompat.getColor(this, R.color.navy))
        }
    }


}