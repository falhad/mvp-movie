package dev.falhad.movieshowcase.ui.hidden

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import dev.falhad.movieshowcase.R
import dev.falhad.movieshowcase.adapter.MovieListAdapter
import dev.falhad.movieshowcase.adapter.MovieListMode
import dev.falhad.movieshowcase.databinding.ActivityHiddenBinding
import dev.falhad.movieshowcase.utils.openMovie
import dev.falhad.movieshowcase.utils.showSnackBar
import dev.falhad.movieshowcase.utils.toArrayList
import org.koin.androidx.viewmodel.ext.android.viewModel

class HiddenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHiddenBinding

    private val hiddenViewModel by viewModel<HiddenViewModel>()

    private val hiddenAdapter by lazy {
        MovieListAdapter(onItemClicked = {
            openMovie(it)
        }, onFavClicked = {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivityHiddenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.back.setOnClickListener { onBackPressed() }

        hiddenAdapter.changeMode(MovieListMode.ROW)
        binding.hiddenResultRv.layoutManager = LinearLayoutManager(this)
        binding.hiddenResultRv.adapter = hiddenAdapter


        hiddenViewModel.hiddenResult.observe(this) { presenter ->
            if (presenter.isNotEmpty()) {
                hiddenAdapter.update(presenter.toArrayList())
            } else {
                //show there is no hidden in list layout
                showSnackBar(getString(R.string.there_is_no_hidden_in_this_list))
            }
        }

    }


}