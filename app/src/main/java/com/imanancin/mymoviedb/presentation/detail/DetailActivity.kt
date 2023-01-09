package com.imanancin.mymoviedb.presentation.detail

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.navArgs
import coil.load
import com.imanancin.mymoviedb.R
import com.imanancin.mymoviedb.databinding.ActivityDetailBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailActivity : AppCompatActivity() {

    private val viewModel: DetailViewModel by viewModel()
    private var isFavorite =  MutableLiveData(false)

    private val args by navArgs<DetailActivityArgs>()
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupUI()
        setupViewModel()
        setUpView()

    }

    private fun setupViewModel() {

        viewModel.isFavorite(args.movie).observe(this) { isFav ->
            Log.e("FAV", isFav.toString(), )
            isFavorite.value = isFav
            Log.e("FAV LIVEDATA", isFavorite.value.toString())
        }

    }

    private fun setUpView() {
        with(binding) {
            backdrop.load("https://image.tmdb.org/t/p/original${args.movie.backdrop}")
            posterMovieDetail.load("https://image.tmdb.org/t/p/original${args.movie.poster}")
            titleMovieDetail.text = args.movie.title
            yearMovieDetail.text = args.movie.release
            genreMovieDetail.text = args.movie.genre
            descMovieDetail.text = args.movie.overview
            Log.e("FAV LIVEDATAA", isFavorite.value.toString())
            isFavorite.observe(this@DetailActivity) {
                if(it == true) {
                    binding.addToFavorite.text = resources.getString(R.string.remove_from_favorite)
                    binding.addToFavorite.setOnClickListener {
                        lifecycleScope.launch {
                            viewModel.deleteFavorite(args.movie)
                        }
                    }
                } else {
                    binding.addToFavorite.text = resources.getString(R.string.add_to_favorite)
                    binding.addToFavorite.setOnClickListener {
                        lifecycleScope.launch {
                            viewModel.addToFavorite(args.movie)
                        }
                    }
                }
            }

        }
    }

    private fun setupUI() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
    }
}