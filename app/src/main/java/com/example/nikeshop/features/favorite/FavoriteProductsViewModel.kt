package com.example.nikeshop.features.favorite

import NikeSingleObserver
import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.common.NikeCompletableObserver
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.model.Product
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class FavoriteProductsViewModel(private val productRepository: ProductRepository) :
    NikeViewModel() {

    val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getFavoriteProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }


            })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.deleteFavoriteProducts(product)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : NikeCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("removeFromFavorites compeleted")
                }

            })
    }
}




