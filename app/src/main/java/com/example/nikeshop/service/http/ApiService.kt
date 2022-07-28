package com.example.nikeshop.service.http

import com.example.nikeshop.data.model.Banner
import com.example.nikeshop.data.model.Product
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort")sort:String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>
}

fun createApiServiceInstance():ApiService{

    val retrofit=Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    return  retrofit.create(ApiService::class.java)
}