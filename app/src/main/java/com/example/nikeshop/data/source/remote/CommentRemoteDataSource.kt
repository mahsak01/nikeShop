package com.example.nikeshop.data.source.remote

import com.example.nikeshop.data.model.Comment
import com.example.nikeshop.data.source.CommentDataSource
import com.example.nikeshop.service.http.ApiService
import io.reactivex.Single

class CommentRemoteDataSource(val apiService: ApiService):CommentDataSource {
    override fun getAll(productId:Int): Single<List<Comment>> =apiService.getComments(productId.toString())
    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }

}