package com.example.nikeshop.data.source.local

import androidx.room.*
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ProductLocalDataSource: ProductDataSource {
    override fun getProducts(sort:Int): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    @Query("SELECT * from products")
    override fun getFavoriteProducts(): Single<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    override fun addFavoriteProducts(product: Product): Completable

    @Delete
    override fun deleteFavoriteProducts(product: Product): Completable
}