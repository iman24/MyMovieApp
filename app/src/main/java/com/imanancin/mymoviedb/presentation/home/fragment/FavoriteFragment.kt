package com.imanancin.mymoviedb.presentation.home.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.imanancin.mymoviedb.core.ui.MovieAdapter
import com.imanancin.mymoviedb.databinding.FragmentFavoriteBinding
import com.imanancin.mymoviedb.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class FavoriteFragment : Fragment() {

    private lateinit var movieAdapter: MovieAdapter
    private val viewModels: HomeViewModel by viewModel()
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return _binding?.root
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
            Log.e("TAG", movieAdapter.itemCount.toString() , )
        }
    }
}