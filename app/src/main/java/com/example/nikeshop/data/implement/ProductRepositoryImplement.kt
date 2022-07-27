package com.example.nikeshop.data.implement

import com.example.nikeshop.data.Product
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single


class ProductRepositoryImplement(
    val productRemoteDataSource: ProductDataSource,
    val productLocalDataSource: ProductDataSource
) : ProductRepository {

    override fun getProducts(): Single<List<Product>> = productRemoteDataSource.getProducts()


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