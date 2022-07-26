package com.example.nikeshop.service.http

import com.example.nikeshop.data.Product
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import rx.Single

interface ApiService {

    @GET("product/list")
    fun getProducts():Single<List<Product>>
}

fun createApiServiceInstance():ApiService{

    val retrofit=Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    return  retrofit.create(ApiService::class.java)
}