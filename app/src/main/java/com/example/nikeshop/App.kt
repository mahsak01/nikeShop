package com.example.nikeshop

import android.app.Application
import android.os.Bundle
import com.example.nikeshop.data.Repository.BannerRepository
import com.example.nikeshop.data.Repository.CommentRepository
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.implement.BannerRepositoryImplement
import com.example.nikeshop.data.implement.CommentRepositoryImplement
import com.example.nikeshop.data.implement.ProductRepositoryImplement
import com.example.nikeshop.data.source.local.ProductLocalDataSource
import com.example.nikeshop.data.source.remote.BannerRemoteDataSource
import com.example.nikeshop.data.source.remote.CommentRemoteDataSource
import com.example.nikeshop.data.source.remote.ProductRemoteDataSource
import com.example.nikeshop.features.list.ProductListViewModel
import com.example.nikeshop.features.main.MainViewModel
import com.example.nikeshop.features.main.ProductListAdapter
import com.example.nikeshop.features.product.ProductDetailViewModel
import com.example.nikeshop.features.product.comment.CommentListViewModel
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
            factory {(viewType:Int)-> ProductListAdapter(viewType,get()) }
            factory<BannerRepository> {
                BannerRepositoryImplement(BannerRemoteDataSource(get()))
            }

            factory<CommentRepository> { CommentRepositoryImplement(CommentRemoteDataSource(get())) }
            viewModel { MainViewModel(get(), get()) }
            viewModel { (bundle: Bundle)->ProductDetailViewModel(bundle,get()) }
            viewModel { (productId: Int)->CommentListViewModel(productId,get()) }
            viewModel { (sort:Int)-> ProductListViewModel(sort,get()) }


        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }
    }
}