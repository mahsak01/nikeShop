package com.example.nikeshop.data.source

import com.example.nikeshop.data.model.Comment
import io.reactivex.Single

interface CommentDataSource {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert(): Single<Comment>
}