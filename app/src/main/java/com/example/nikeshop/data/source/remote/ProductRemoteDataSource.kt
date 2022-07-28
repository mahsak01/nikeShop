package com.example.nikeshop.data.source.remote

import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.source.ProductDataSource
import com.example.nikeshop.service.http.ApiService
import io.reactivex.Completable
import io.reactivex.Single


class ProductRemoteDataSource(val apiService: ApiService):ProductDataSource {

    override fun getProducts(sort:Int): Single<List<Product>> = apiService.getProducts(sort.toString())

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addFavoriteProducts(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFavoriteProducts(): Completable {
        TODO("Not yet implemented")
    }
}