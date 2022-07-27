package com.example.nikeshop

import android.app.Application
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.implement.ProductRepositoryImplement
import com.example.nikeshop.data.source.local.ProductLocalDataSource
import com.example.nikeshop.data.source.remote.ProductRemoteDataSource
import com.example.nikeshop.features.main.MainViewModel
import com.example.nikeshop.service.http.ApiService
import com.example.nikeshop.service.http.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant()
        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            factory<ProductRepository> {
                ProductRepositoryImplement(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}