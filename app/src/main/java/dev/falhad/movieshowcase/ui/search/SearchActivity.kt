package dev.falhad.movieshowcase.ui.search

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
import dev.falhad.movieshowcase.databinding.ActivitySearchBinding
import dev.falhad.movieshowcase.model.db.entity.MovieEntity
import dev.falhad.movieshowcase.network.Presenter
import dev.falhad.movieshowcase.ui.home.HomeViewModel
import dev.falhad.movieshowcase.utils.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchBinding

    private val searchViewModel by viewModel<SearchViewModel>()

    private val searchAdapter by lazy {
        MovieListAdapter(onItemClicked = {
            openMovie(it)
        }, onFavClicked = {

        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hideSearchPb()

        hideMsgBox()
        binding.clearIv.invisible()
        binding.clearIv.setOnClickListener {
            searchAdapter.clear()
            binding.searchEt.setText("")
            binding.searchEt.requestFocus()
        }

        binding.srl.setOnRefreshListener {
            binding.srl.isRefreshing = false
            refresh()
        }
        binding.back.setOnClickListener { onBackPressed() }

        searchAdapter.changeMode(MovieListMode.ROW)
        binding.searchResultRv.layoutManager = LinearLayoutManager(this)
        binding.searchResultRv.adapter = searchAdapter


        searchViewModel.searchResult.observe(this) { presenter ->
            when (presenter) {
                is Presenter.Error -> {
                    onSearchError(presenter)
                }
                Presenter.Loading -> {
                    onSearchLoading()
                }
                is Presenter.Success -> {
                    val term = binding.searchEt.text.toString()
                    binding.searchResultRv.hideShimmerAdapter()
                    val items = presenter.item.toArrayList()
                    hideSearchPb()
                    if (items.isEmpty()) {
                        if (term.isEmpty()) {
                            onSearchHint(term)
                        } else {
                            onNoResultWasFound(term)
                        }
                    } else {
                        onSearchResult(items)
                    }
                }
            }
        }


        binding.searchEt.addTextChangedListener {
            val term = binding.searchEt.text.toString()
            if (term.isNotEmpty()) {
                binding.clearIv.visible()
            } else {
                binding.clearIv.invisible()
            }
            searchViewModel.search(term)
        }

        binding.searchEt.requestFocus()

    }

    private fun hideSearchPb() {
        binding.searchPb.invisible()
        binding.searchIv.visible()
    }

    private fun showSearchPb() {
        binding.searchPb.visible()
        binding.searchIv.invisible()
    }


    private fun onSearchResult(items: ArrayList<MovieEntity>) {
        hideMsgBox()
        searchAdapter.update(items)
    }

    private fun onSearchHint(term: String) {
        showMsgBox(
            getString(R.string.type_to_find),
            getString(R.string.type_term_to_find).format(
                term
            ),
            getString(R.string.search_now)
        ) {
            binding.searchEt.setText("")
            binding.searchEt.requestFocus()
        }
    }

    private fun onNoResultWasFound(term: String) {
        showMsgBox(
            getString(R.string.no_results),
            getString(R.string.no_results_was_found_for_s_please_search_with_another_term).format(
                "`$term`"
            ), getString(R.string.search_now)
        ) {
            binding.searchEt.setText("")
            binding.searchEt.requestFocus()
        }
    }

    private fun onSearchLoading() {
        searchAdapter.clear()
        showSearchPb()
        hideMsgBox()
        binding.searchResultRv.showShimmerAdapter()
    }

    private fun onSearchError(presenter: Presenter.Error) {
        hideSearchPb()
        searchAdapter.clear()
        binding.searchResultRv.hideShimmerAdapter()
        showMsgBox(
            getString(R.string.error_happened),
            presenter.msg ?: getString(R.string.unknown_error),
            getString(R.string.try_again)
        ) {
            refresh()
        }
    }


    private fun showMsgBox(title: String, desc: String, btn: String, onClick: () -> Unit) {
        binding.msgBox.visible()
        binding.msgTitle.text = title
        binding.msgDesc.text = desc
        binding.msgBtn.text = btn
        binding.msgBtn.setOnClickListener { onClick() }
    }

    private fun hideMsgBox() {
        binding.msgBox.gone()
        binding.searchResultRv.visible()
    }


    override fun onResume() {
        super.onResume()
        refresh()
    }

    private fun refresh() {
        val term = binding.searchEt.text.toString()
        if (term.isNotEmpty()) {
            searchViewModel.searchByTerm(term)
        }
    }


}