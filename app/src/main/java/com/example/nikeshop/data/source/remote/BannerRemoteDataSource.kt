package com.example.nikeshop.data.source.remote

import com.example.nikeshop.data.model.Banner
import com.example.nikeshop.data.source.BannerDataSource
import com.example.nikeshop.service.http.ApiService
import io.reactivex.Single


class BannerRemoteDataSource(val apiService: ApiService):BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()

}