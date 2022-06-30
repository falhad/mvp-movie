package dev.falhad.movieshowcase.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.falhad.movieshowcase.adapter.MovieListAdapter
import dev.falhad.movieshowcase.adapter.MovieListMode
import dev.falhad.movieshowcase.databinding.FragmentHomeBinding
import dev.falhad.movieshowcase.network.Presenter
import dev.falhad.movieshowcase.ui.main.MainActivity
import dev.falhad.movieshowcase.ui.favorite.FavoriteActivity
import dev.falhad.movieshowcase.ui.search.SearchActivity
import dev.falhad.movieshowcase.utils.gone
import dev.falhad.movieshowcase.utils.openMovie
import dev.falhad.movieshowcase.utils.toArrayList
import dev.falhad.movieshowcase.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel by viewModel<HomeViewModel>()

    private val trendingAdapter by lazy {
        MovieListAdapter(onItemClicked = {
            openMovie(it)
        }, onFavClicked = {

        })
    }

    private val favoritesAdapter by lazy {
        MovieListAdapter(onItemClicked = {
            openMovie(it)
        }, onFavClicked = {

        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchCv.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    SearchActivity::class.java
                )
            )
        }

        binding.menu.setOnClickListener { (activity as? MainActivity)?.openDrawer() }

        binding.srl.setOnRefreshListener {
            binding.srl.isRefreshing = false
            homeViewModel.fetchTrending()
        }
        //trends
        binding.trendingRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.trendingRv.adapter = trendingAdapter
        trendingAdapter.changeMode(MovieListMode.CARD)


        homeViewModel.trending.observe(viewLifecycleOwner) { presenter ->
            when (presenter) {
                is Presenter.Error -> {
                    binding.trendingRv.hideShimmerAdapter()
                }
                Presenter.Loading -> {
                    binding.trendingRv.showShimmerAdapter()
                }
                is Presenter.Success -> {
                    binding.trendingRv.hideShimmerAdapter()
                    trendingAdapter.update(presenter.item.toArrayList())
                }
            }
        }

        //favorites
        binding.favoritesRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.favoritesRv.adapter = favoritesAdapter
        favoritesAdapter.changeMode(MovieListMode.CARD)


        binding.moreLb.setOnClickListener {
            startActivity(
                Intent(
                    requireContext(),
                    FavoriteActivity::class.java
                )
            )
        }
        binding.moreIc.setOnClickListener { binding.moreLb.performClick() }

        homeViewModel.favorites.observe(viewLifecycleOwner) { items ->

            if (items.isNullOrEmpty()) {
                binding.favoritesRv.gone()
                binding.favLabel.gone()
                binding.favoritesIc.gone()
                binding.moreIc.gone()
                binding.moreLb.gone()
            } else {
                binding.favoritesRv.visible()
                binding.favLabel.visible()
                binding.favoritesIc.visible()
                binding.moreIc.visible()
                binding.moreLb.visible()
                favoritesAdapter.update(items.toArrayList())
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        homeViewModel.fetchTrending()
    }

}