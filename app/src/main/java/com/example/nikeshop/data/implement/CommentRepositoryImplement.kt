package com.example.nikeshop.data.implement

import com.example.nikeshop.data.Repository.CommentRepository
import com.example.nikeshop.data.model.Comment
import com.example.nikeshop.data.source.CommentDataSource
import io.reactivex.Single

class CommentRepositoryImplement(val commentRemoteDataSource: CommentDataSource):CommentRepository {
    override fun getAll(productId:Int): Single<List<Comment>> =commentRemoteDataSource.getAll(productId)
    override fun insert(): Single<Comment> {
        TODO("Not yet implemented")
    }


}