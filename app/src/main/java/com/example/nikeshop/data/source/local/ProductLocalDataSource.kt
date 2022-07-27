package com.example.nikeshop.data.source.local

import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single


class ProductLocalDataSource: ProductDataSource {
    override fun getProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

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