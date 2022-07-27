package com.example.nikeshop.data.source

import com.example.nikeshop.data.model.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners(): Single<List<Banner>>
}