package com.imanancin.mymoviedb

import android.app.Application
import com.imanancin.core.di.databaseModule
import com.imanancin.core.di.networkModule
import com.imanancin.core.di.repositoryModule
import com.imanancin.core.di.useCaseModule
import com.imanancin.mymoviedb.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    useCaseModule,
                    repositoryModule,
                    appModule
                    )
            )
        }
    }
}