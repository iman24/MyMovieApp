package com.imanancin.favorite.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.imanancin.core.ui.MovieAdapter
import com.imanancin.favorite.databinding.FragmentFavoriteBinding
import com.imanancin.favorite.di.favoriteModule
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules


class FavoriteFragment : Fragment() {

    private val loadFeatures by lazy { loadKoinModules(favoriteModule) }
    private fun injectFeatures() = loadFeatures

    private lateinit var movieAdapter: MovieAdapter
    private val viewModels: FavoriteViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFeatures()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupToolbar()
        setupAdapter()
    }

    private fun setupToolbar() {
        activity?.setActionBar(binding?.toolbar)
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter { movie ->
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailActivity(movie)
            findNavController().navigate(action)
        }

        binding?.rvMovieFavorite?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
        }


        viewModels.getFavoriteMovies().observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvMovieFavorite?.adapter = null
        _binding = null
    }
}