package com.example.nikeshop.features.main

import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.common.NikeSingleObserver
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.common.SORT_LATEST
import com.example.nikeshop.common.SORT_POPULAR
import com.example.nikeshop.data.Repository.BannerRepository
import com.example.nikeshop.data.model.Product
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.model.Banner
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainViewModel( productRepository: ProductRepository ,  bannerRepository: BannerRepository) :NikeViewModel() {

    val latestProductLiveData= MutableLiveData<List<Product>>()
//    val popularProductLiveData= MutableLiveData<List<Product>>()

    val bannerLiveData= MutableLiveData<List<Banner>>()

    init {
        progressLiveData.value=true
        productRepository.getProducts(SORT_LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally{progressLiveData.value=false}
            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                      latestProductLiveData.value=t
                }
            })

//        productRepository.getProducts(SORT_POPULAR)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doFinally{progressLiveData.value=false}
//            .subscribe(object :NikeSingleObserver<List<Product>>(compositeDisposable){
//                override fun onSuccess(t: List<Product>) {
//                    popularProductLiveData.value=t
//                }
//            })


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