package com.example.nikeshop.data.implement

import com.example.nikeshop.data.Repository.BannerRepository
import com.example.nikeshop.data.model.Banner
import com.example.nikeshop.data.source.remote.BannerRemoteDataSource
import io.reactivex.Single

class BannerRepositoryImplement(val bannerRemoteDataSource: BannerRemoteDataSource):BannerRepository {
    override fun getBanners(): Single<List<Banner>> =bannerRemoteDataSource.getBanners()
}