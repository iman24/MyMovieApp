package com.imanancin.mymoviedb.ui.home.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.Chip
import com.imanancin.core.domain.model.MovieType
import com.imanancin.core.ui.MovieAdapter
import com.imanancin.mymoviedb.R
import com.imanancin.mymoviedb.databinding.FragmentHomeBinding
import com.imanancin.mymoviedb.ui.home.HomeViewModel
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
        return binding?.root
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

        currentCategory.value?.let { viewModels.setMovieType(it) }
        currentCategory.value = MovieType.valueOf(MovieType.NOW_PLAYING.name)
        viewModels.movies().observe(viewLifecycleOwner) {
            movieAdapter.submitData(lifecycle, it)
        }
    }

    private fun setupCategoryUi() {
        val categoryList = MovieType.values()
        categoryList.forEach { movieType ->
            val chip = Chip(requireActivity()).apply {
                currentCategory.observe(viewLifecycleOwner) {
                    isChecked = it == MovieType.valueOf(movieType.name)
                }
                isClickable = true
                isCheckable = true
                chipBackgroundColor = AppCompatResources.getColorStateList(context, R.color.chip_state_bg)
                setTextColor(AppCompatResources.getColorStateList(context, R.color.chip_state_text))
                text = movieType.name.replace("_", " ")
                setOnClickListener {
                    viewModels.setMovieType(MovieType.valueOf(movieType.name))
                    currentCategory.value = MovieType.valueOf(movieType.name)


                }
            }
            binding?.movieCategory?.addView(chip)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvMovie?.adapter = null
        _binding = null
    }
}