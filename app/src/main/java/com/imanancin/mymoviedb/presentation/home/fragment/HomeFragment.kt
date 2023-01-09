package com.imanancin.mymoviedb.presentation.home.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.imanancin.mymoviedb.core.domain.model.MovieType
import com.imanancin.mymoviedb.core.ui.MovieAdapter
import com.imanancin.mymoviedb.databinding.FragmentHomeBinding
import com.imanancin.mymoviedb.presentation.home.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private val viewModels: HomeViewModel by viewModel()
    private lateinit var movieAdapter: MovieAdapter

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding

    private var currentCategory: MutableLiveData<MovieType> = MutableLiveData(MovieType.NOW_PLAYING)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupCategoryUi()
        setupAdapter()

    }

    private fun setupToolbar() {
        activity?.setActionBar(binding?.toolbar)
    }

    private fun setupAdapter() {
        movieAdapter = MovieAdapter { movie ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailActivity(movie)
            findNavController().navigate(action)
        }

        binding?.rvMovie?.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
        }

        viewModels.setMovieType(MovieType.NOW_PLAYING)
        viewModels.movies().observe(viewLifecycleOwner) {
            Log.e("TAG", "setupAdapter: ", )
            movieAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupCategoryUi() {
        val categoryList = MovieType.values()
        categoryList.filter {
            it != MovieType.SEARCH
        }.forEach { movieType ->
            val chip = Chip(requireActivity()).apply {
                if(currentCategory.value == movieType) {
                    isChecked = true
                    setBackgroundColor(Color.BLUE)
                }
                text = movieType.name.replace("_", " ")
                setOnClickListener {
                    viewModels.setMovieType(MovieType.valueOf(movieType.name))
                    currentCategory.value = MovieType.valueOf(movieType.name)
                }
            }
            binding?.movieCategory?.addView(chip)
        }
    }


}