package com.imanancin.mymoviedb.core.di

import android.util.Log
import androidx.room.Room
import com.imanancin.mymoviedb.BuildConfig
import com.imanancin.mymoviedb.core.domain.repository.IMovieRepository
import com.imanancin.mymoviedb.core.domain.usecase.MovieInteractor
import com.imanancin.mymoviedb.core.domain.usecase.MovieUseCase
import com.imanancin.mymoviedb.core.utils.AppExecutors
import com.imanancin.mymoviedb.data.MovieRepositoryImpl
import com.imanancin.mymoviedb.data.source.local.LocalDataSource
import com.imanancin.mymoviedb.data.source.local.room.MovieDatabase
import com.imanancin.mymoviedb.data.source.remote.RemoteDataSource
import com.imanancin.mymoviedb.data.source.remote.network.ApiService
import com.imanancin.mymoviedb.presentation.detail.DetailViewModel
import com.imanancin.mymoviedb.presentation.home.HomeViewModel
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val API_URL = "https://api.themoviedb.org/3/"
const val APIKEY = "4c4422eccc3076b50ad5f9462c102731"


val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

        val clientInterceptor = Interceptor { chain: Interceptor.Chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", APIKEY)
                .build()
            request = request.newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }

        OkHttpClient.Builder()
            .addNetworkInterceptor(clientInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        Log.e("TAG", API_URL )
        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {

    factory { get<MovieDatabase>().favoriteMovieDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Moviess.db"
        ).fallbackToDestructiveMigration().build()
    }



}

val viewModelmodule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}

val useCaseModule = module {
    factory<MovieUseCase> { MovieInteractor(get()) }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<IMovieRepository> { MovieRepositoryImpl(get(), get()) }
}
