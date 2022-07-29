package com.example.nikeshop.data.Repository

import com.example.nikeshop.data.model.Comment
import io.reactivex.Single

interface CommentRepository {

    fun getAll(productId:Int): Single<List<Comment>>

    fun insert():Single<Comment>
}