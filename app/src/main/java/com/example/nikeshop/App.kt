package com.example.nikeshop

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.example.nikeshop.data.Repository.*
import com.example.nikeshop.data.implement.*
import com.example.nikeshop.data.source.UserDataSource
import com.example.nikeshop.data.source.local.ProductLocalDataSource
import com.example.nikeshop.data.source.local.UserLocalDataSource
import com.example.nikeshop.data.source.remote.*
import com.example.nikeshop.features.auth.AuthViewModel
import com.example.nikeshop.features.cart.CartViewModel
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
import io.reactivex.Single
import org.koin.android.ext.android.get
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
            factory { (viewType: Int) -> ProductListAdapter(viewType, get()) }
            factory<BannerRepository> {
                BannerRepositoryImplement(BannerRemoteDataSource(get()))
            }
            single<SharedPreferences> {
                this@App.getSharedPreferences(
                    "app_setting",
                    MODE_PRIVATE
                )
            }
            single <UserDataSource>{UserLocalDataSource(get())}
            single<UserRepository> {
                UserRepositoryImplement(UserLocalDataSource(get()), UserRemoteDataSource(get()))
            }
            factory<CartRepository> { CartRepositoryImplement(CartRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImplement(CommentRemoteDataSource(get())) }

            viewModel { MainViewModel(get(), get()) }
            viewModel { (bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel { (productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel { (sort: Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel {CartViewModel(get())}


        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        val userRepository:UserRepository=get()
        userRepository.loadToken()
    }
}