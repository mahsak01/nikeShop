package com.example.nikeshop.data.implement

import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single


class ProductRepositoryImplement(
    val productRemoteDataSource: ProductDataSource,
    val productLocalDataSource: ProductDataSource
) : ProductRepository {

    override fun getProducts(sort: Int): Single<List<Product>> =
        productLocalDataSource.getFavoriteProducts().flatMap { favoriteProducts ->
            productRemoteDataSource.getProducts(sort).doOnSuccess {
                val favoriteProductsId = favoriteProducts.map { it.id }
                it.forEach { product ->
                    if (favoriteProductsId.contains(product.id))
                        product.isFavorite = true
                }
            }
        }


    override fun getFavoriteProducts(): Single<List<Product>> =
        productLocalDataSource.getFavoriteProducts()

    override fun addFavoriteProducts(product: Product): Completable =
        productLocalDataSource.addFavoriteProducts(product)

    override fun deleteFavoriteProducts(product: Product): Completable =
        productLocalDataSource.deleteFavoriteProducts(product)
}