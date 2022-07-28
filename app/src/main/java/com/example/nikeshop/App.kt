package com.example.nikeshop

import android.app.Application
import com.example.nikeshop.data.Repository.BannerRepository
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.implement.BannerRepositoryImplement
import com.example.nikeshop.data.implement.ProductRepositoryImplement
import com.example.nikeshop.data.source.local.ProductLocalDataSource
import com.example.nikeshop.data.source.remote.BannerRemoteDataSource
import com.example.nikeshop.data.source.remote.ProductRemoteDataSource
import com.example.nikeshop.features.main.MainViewModel
import com.example.nikeshop.features.main.ProductListAdapter
import com.example.nikeshop.service.http.ApiService
import com.example.nikeshop.service.http.FrescoLoadingServiceImplement
import com.example.nikeshop.service.http.ImageLoadingService
import com.example.nikeshop.service.http.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single<ApiService> { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoLoadingServiceImplement() }
            factory<ProductRepository> {
                ProductRepositoryImplement(
                    ProductRemoteDataSource(get()),
                    ProductLocalDataSource()
                )
            }
            factory { ProductListAdapter(get()) }
            factory<BannerRepository> {
                BannerRepositoryImplement(BannerRemoteDataSource(get()))
            }
            viewModel { MainViewModel(get(), get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}