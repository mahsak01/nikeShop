package com.example.nikeshop.data.Repository

import com.example.nikeshop.data.model.AddToCartResponse
import com.sevenlearn.nikestore.data.CartItemCount
import com.sevenlearn.nikestore.data.CartResponse
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single

interface CartRepository {

    fun addToCart(productId:Int):Single<AddToCartResponse>
    fun get():Single<CartResponse>
    fun remove(cartItemId:Int):Single<MessageResponse>
    fun changeCount(cartItemId:Int , count:Int):Single<AddToCartResponse>
    fun getCartItemsCount():Single<CartItemCount>
}