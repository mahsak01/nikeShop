package com.example.nikeshop.features.list

import NikeSingleObserver
import androidx.lifecycle.MutableLiveData
import com.example.nikeshop.R
import com.example.nikeshop.common.NikeViewModel
import com.example.nikeshop.data.Repository.ProductRepository
import com.example.nikeshop.data.model.Product
import com.sevenlearn.nikestore.common.asyncNetworkRequest
import java.util.*

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) :
    NikeViewModel() {

    val productLiveData = MutableLiveData<List<Product>>()
    val selectedSortTitleLiveData = MutableLiveData<Int>()
    val sortTitles = arrayOf(
        R.string.sortLatest,
        R.string.sortPopular,
        R.string.sortPriceHighToLow,
        R.string.sortPriceLowToHigh
    )

    init {
        getProduct()
        selectedSortTitleLiveData.value = sortTitles[sort]

    }

    fun getProduct() {
        progressLiveData.value = true
        productRepository.getProducts(sort)
            .asyncNetworkRequest()
            .doFinally { progressLiveData.value = false }
            .subscribe(object : NikeSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productLiveData.value = t
                }

            })
    }

    fun onSelectedSortChangeByUser(sort: Int) {
        this.sort = sort
        this.selectedSortTitleLiveData.value = sortTitles[sort]
        getProduct()
    }
}