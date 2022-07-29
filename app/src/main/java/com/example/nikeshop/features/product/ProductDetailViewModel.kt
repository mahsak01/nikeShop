package com.example.nikeshop.features.product

import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.example.nikeshop.common.EXTRA_KEY_DATA
import com.example.nikeshop.common.NikeSingleObserver
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.CommentRepository
import com.example.nikeshop.data.model.Comment
import com.example.nikeshop.data.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductDetailViewModel(
    private val bundle: Bundle,
    private val commentRepository: CommentRepository
) : NikeViewModel() {

    val productLiveData = MutableLiveData<Product>()
    val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
        commentRepository.getAll(productLiveData.value!!.id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }

            })
    }
}