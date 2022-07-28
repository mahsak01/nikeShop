package com.example.nikeshop.data.source

import com.example.nikeshop.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductDataSource {

    fun getProducts(sort:Int): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addFavoriteProducts(): Completable

    fun deleteFavoriteProducts(): Completable


}