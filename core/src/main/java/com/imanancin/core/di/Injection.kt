package com.imanancin.core.di


import androidx.room.Room
import com.imanancin.core.BuildConfig
import com.imanancin.core.data.MovieRepositoryImpl
import com.imanancin.core.data.source.local.LocalDataSource
import com.imanancin.core.data.source.local.room.MovieDatabase
import com.imanancin.core.data.source.remote.RemoteDataSource
import com.imanancin.core.data.source.remote.network.ApiService
import com.imanancin.core.domain.repository.IMovieRepository
import com.imanancin.core.domain.usecase.MovieInteractor
import com.imanancin.core.domain.usecase.MovieUseCase
import com.imanancin.core.utils.AppExecutors
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



val networkModule = module {
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE)

        val clientInterceptor = Interceptor { chain: Interceptor.Chain ->
            var request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder()
                .addQueryParameter("api_key", BuildConfig.API_KEY)
                .build()
            request = request.newBuilder()
                .url(url)
                .build()
            chain.proceed(request)
        }

        val hostname = "api.themoviedb.org"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname,"sha256/p+WeEuGncQbjSKYPSzAaKpF/iLcOjFLuZubtsXupYSI=")
            .add(hostname,"sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
            .add(hostname,"sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
            .add(hostname,"sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
            .build()

        OkHttpClient.Builder()
            .addNetworkInterceptor(clientInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .certificatePinner(certificatePinner)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {

    factory { get<MovieDatabase>().favoriteMovieDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes(BuildConfig.PASS_KEY.toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            MovieDatabase::class.java, "Moviess.db"
        )
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }



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
