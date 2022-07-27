package com.example.nikeshop.features.main

import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.common.NikeSingleObserver
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.BannerRepository
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.model.Banner
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class MainViewModel( productRepository: ProductRepository ,  bannerRepository: BannerRepository) :NikeViewModel() {

    val productLiveData= MutableLiveData<List<Product>>()
    val bannerLiveData= MutableLiveData<List<Banner>>()

    init {
        progressLiveData.value=true
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressLiveData.value=false}
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                      productLiveData.value=t
                }
            })

        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :NikeSingleObserver<List<Banner>>(compositeDisposable){

                override fun onSuccess(t: List<Banner>) {
                    bannerLiveData.value=t
                }


            })

    }

}