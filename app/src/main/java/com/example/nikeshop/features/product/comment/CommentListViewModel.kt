package com.example.nikeshop.features.product.comment

import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.common.NikeSingleObserver
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.CommentRepository
import com.example.nikeshop.data.model.Comment
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class CommentListViewModel(val productId: Int, val commentRepository: CommentRepository) :
    NikeViewModel() {

        val  commentsLiveData=MutableLiveData<List<Comment>>()

    init {
        commentRepository.getAll(productId)
            .asyncNetworkRequest()
            .subscribe(object :NikeSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value=t
                }

            })
    }

}