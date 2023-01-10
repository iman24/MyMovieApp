package com.imanancin.mymoviedb.di

import com.imanancin.mymoviedb.ui.detail.DetailViewModel
import com.imanancin.mymoviedb.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}