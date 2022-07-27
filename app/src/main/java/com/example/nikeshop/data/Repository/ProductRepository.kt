package com.example.nikeshop.data.Repository

import com.example.nikeshop.data.model.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(): Single<List<Product>>

    fun getFavoriteProducts():Single<List<Product>>

    fun addFavoriteProducts(): Completable

    fun deleteFavoriteProducts():Completable


}