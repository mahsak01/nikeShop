package com.example.nikeshop.features.main

import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Product
import com.example.nikeshop.data.Repository.ProductRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class MainViewModel(val productRepository: ProductRepository) :NikeViewModel() {

    val productLiveData= MutableLiveData<List<Product>>()

    init {
        progressLiveData.value=true
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressLiveData.value=false}
            .subscribe(object :SingleObserver<List<Product>>{
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }

                override fun onSuccess(t: List<Product>) {
                      productLiveData.value=t
                }

                override fun onError(e: Throwable) {
                     Timber.e(e)
                }

            })
    }

}