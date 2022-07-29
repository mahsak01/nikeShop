package com.example.nikeshop.service.http

import com.example.nikeshop.data.model.*
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.sevenlearn.nikestore.data.MessageResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort")sort:String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id")productId:String):Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject):Single<AddToCartResponse>

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject):Single<TokenResponse>

    @POST("user/register")
    fun signUp(@Body jsonObject: JsonObject):Single<MessageResponse>

}

fun createApiServiceInstance():ApiService{

    val okHttpClient=OkHttpClient.Builder()
        .addInterceptor{
            val oldRequest=it.request()
            val newRequestBuilder=oldRequest.newBuilder()
            if (TokenContainer.token!=null)
                newRequestBuilder.addHeader("Authorization","Bearer ${TokenContainer.token}")

            newRequestBuilder.addHeader("Accept","application/json")
            newRequestBuilder.method(oldRequest.method,oldRequest.body)
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }.build()

    val retrofit=Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    return  retrofit.create(ApiService::class.java)
}