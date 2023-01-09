package com.imanancin.mymoviedb.data.source.remote.network

import com.imanancin.mymoviedb.BuildConfig
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ApiConfig {

    const val API_URL = "https://api.themoviedb.org/3/"
    const val APIKEY = "4c4422eccc3076b50ad5f9462c102731"


    private fun provideOkHttp(): OkHttpClient {
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

        return OkHttpClient.Builder()
            .addNetworkInterceptor(clientInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    fun provideApiService(): ApiService {

        val retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttp())
            .build()
        return retrofit.create(ApiService::class.java)
    }

}