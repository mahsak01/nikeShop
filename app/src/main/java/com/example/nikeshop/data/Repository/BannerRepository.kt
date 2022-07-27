package com.example.nikeshop.data.Repository

import com.example.nikeshop.data.model.Banner
import io.reactivex.Single

interface BannerRepository {

    fun getBanners():Single<List<Banner>>
}