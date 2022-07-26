package com.example.nikeshop.data.implement

import com.example.nikeshop.data.Repository.CartRepository
import com.example.nikeshop.data.model.AddToCartResponse
import com.example.nikeshop.data.source.CartDataSource
import com.example.nikeshop.data.source.remote.CartRemoteDataSource
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single

class CartRepositoryImplement(val cartRemoteDataSource: CartDataSource) : CartRepository {

    override fun addToCart(productId: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.addToCart(productId)

    override fun get(): Single<CartResponse> = cartRemoteDataSource.get()

    override fun remove(cartItemId: Int): Single<MessageResponse> =
        cartRemoteDataSource.remove(cartItemId)

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        cartRemoteDataSource.changeCount(cartItemId, count)

    override fun getCartItemsCount(): Single<CartItemCount> =
        cartRemoteDataSource.getCartItemsCount()
}